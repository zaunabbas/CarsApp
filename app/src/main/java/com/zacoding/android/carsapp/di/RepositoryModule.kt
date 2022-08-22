package com.zacoding.android.carsapp.di

import com.zacoding.android.carsapp.data.remote.Api
import com.zacoding.android.carsapp.domain.CarsRepository
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
