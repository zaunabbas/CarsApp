package com.sixt.android.cars.di

import com.sixt.android.cars.data.remote.Api
import com.sixt.android.cars.domain.CarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideCarsRepository(
    api: Api
  ): CarsRepository {
    return CarsRepository(api)
  }
}
