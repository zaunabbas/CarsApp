package com.sixt.android.cars.ui.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.sixt.android.cars.R
import com.sixt.android.cars.databinding.ActivityListBinding
import com.sixt.android.cars.ui.list.adapter.AlbumListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    private val listViewModel: ListViewModel by viewModels()

    private lateinit var mAdapter: AlbumListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        observeState()
        observeData()
    }


    private fun initUI() {
        initAdapter()
    }

    private fun initAdapter() {
        //initAdapter
        mAdapter = AlbumListAdapter(arrayListOf())
        binding.list.adapter = mAdapter
        //
    }


    private fun observeState() {

        lifecycleScope.launch {
            listViewModel.isLoading.collect { loading ->

            }
        }
    }

    private fun observeData() {

        lifecycleScope.launch {
            listViewModel.carsGroupFilterList.collectLatest {
                showFilterTags(it)
            }
        }

        lifecycleScope.launch {
            listViewModel.filteredCarsList.collectLatest {
                mAdapter.submitRefreshList(it)
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
            listViewModel.updateFilter(filteredValue)
        }

    }
}
