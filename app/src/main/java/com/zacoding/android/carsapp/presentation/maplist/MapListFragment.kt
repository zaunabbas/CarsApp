package com.zacoding.android.carsapp.presentation.maplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.zacoding.android.carsapp.R
import com.zacoding.android.carsapp.data.model.CarsData
import com.zacoding.android.carsapp.databinding.FragmentMapListBinding
import com.zacoding.android.carsapp.presentation.base.ContentState
import com.zacoding.android.carsapp.presentation.base.EmptyState
import com.zacoding.android.carsapp.presentation.base.ErrorState
import com.zacoding.android.carsapp.presentation.base.LoadingState
import com.zacoding.android.carsapp.presentation.maplist.adapter.AlbumListAdapter
import com.zacoding.android.carsapp.presentation.maplist.adapter.OnItemClickListener
import com.zacoding.android.carsapp.util.gone
import com.zacoding.android.carsapp.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MapListFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @VisibleForTesting
    internal val mapListViewModel: MapListViewModel by viewModels()

    private lateinit var mMap: GoogleMap
    private val markersList = arrayListOf<Marker>()

    private var bsBehavior: BottomSheetBehavior<*>? = null
    private lateinit var mAdapter: AlbumListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupMap()
        observeState()
        observeData()
    }

    private fun initUI() {
        setupUiEventListeners()
        setupBottomSheetBehavior()
        initAdapter()
    }

    private fun setupUiEventListeners() {
        binding.bottomSheetView.btnShowMap.setOnClickListener {
            collapseBottomSheet()
        }
    }

    private fun setupBottomSheetBehavior() {
        bsBehavior = BottomSheetBehavior.from(binding.bottomSheetView.bottomSheetContent)
        bsBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.bottomSheetView.btnShowMap.isGone = true
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.bottomSheetView.btnShowMap.isVisible = true
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //action if needed
            }
        })

        collapseBottomSheet()
    }

    private fun initAdapter() {
        //initAdapter
        mAdapter = AlbumListAdapter(arrayListOf()).apply {
            listener = object : OnItemClickListener {
                override fun onRowClick(position: Int, carsData: CarsData) {
                    collapseBottomSheet()
                    val selectedMarker = markersList[position]
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(selectedMarker.position))
                    selectedMarker.showInfoWindow()
                    showSelectedCarInfo(carsData)
                }

            }
        }
        binding.bottomSheetView.list.adapter = mAdapter
        //
    }

    private fun setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            mapListViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is LoadingState -> {
                        binding.progress.show()
                        binding.hlsGroupFilters.gone()
                        binding.carInfoView.root.gone()
                    }

                    is ContentState -> {
                        binding.progress.gone()
                        binding.map.show()
                        binding.hlsGroupFilters.show()
                    }

                    is EmptyState -> {
                        binding.progress.gone()
                        binding.hlsGroupFilters.gone()
                    }

                    is ErrorState -> {
                        binding.progress.gone()
                        binding.hlsGroupFilters.gone()
                    }

                }
            }
        }
    }

    private fun observeData() {

        lifecycleScope.launchWhenStarted {
            mapListViewModel.carsGroupFiltersList.collectLatest {
                showFilterTags(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            mapListViewModel.filteredCarsList.collectLatest {
                showMapAndListData(it)
            }
        }

    }

    private fun showFilterTags(tags: List<String>) {

        tags.forEachIndexed { index, item ->
            val chip =
                layoutInflater.inflate(R.layout.row_chip_view, binding.chipGroup, false) as Chip
            chip.id = index
            chip.text = item
            binding.chipGroup.addView(chip)
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
            val filteredValue = if (group.checkedChipId >= 0) tags[group.checkedChipId] else null
            mapListViewModel.updateFilter(filteredValue)
        }

    }

    private fun showMapAndListData(list: List<CarsData>) {
        showMapMarkers()
        mAdapter.submitRefreshList(list)

        binding.bottomSheetView.tvCarCount.text =
            String.format(getString(R.string.cars_count), list.size)
    }

    private fun showMapMarkers() {

        if (!this::mMap.isInitialized)
            return

        mMap.clear()
        markersList.clear()
        binding.carInfoView.clCarInfoView.isVisible = false
        mapListViewModel.filteredCarsList.value.forEach { data ->

            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(data.latitude, data.longitude)).title(data.name)
            )

            marker?.let {
                it.tag = data
                markersList.add(it)
            }
        }

        //get the latLng builder from the marker list
        val builder = LatLngBounds.Builder()
        for (m in markersList) {
            builder.include(m.position)
        }

        val padding = 50
        val bounds: LatLngBounds = builder.build()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)

        mMap.animateCamera(cu)

        mMap.setOnMarkerClickListener {
            showSelectedCarInfo(it.tag as CarsData)
            false
        }
    }

    private fun showSelectedCarInfo(carData: CarsData) {
        binding.carInfoView.clCarInfoView.isVisible = true
        binding.carInfoView.let {

            it.tvOwnerName.text = carData.name
            it.tvLp.text = carData.licensePlate
            it.tvCarDetail.text = String.format(
                binding.root.context.getString(R.string.car_details_format),
                carData.make, carData.modelName, carData.color
            )

            Glide.with(binding.root.context)
                .load(carData.carImageUrl)
                .into(it.ivCar)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun collapseBottomSheet() {
        bsBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}
