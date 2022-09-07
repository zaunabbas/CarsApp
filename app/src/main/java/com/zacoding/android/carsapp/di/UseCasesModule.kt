package com.zacoding.android.carsapp.di

import com.zacoding.android.carsapp.domain.repository.CarsRepository
import com.zacoding.android.carsapp.domain.use_case.FetchAllCarsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideFetchAllCarsUseCase(
        carsRepository: CarsRepository
    ): FetchAllCarsUseCase {
        return FetchAllCarsUseCase(carsRepository)
    }
}
