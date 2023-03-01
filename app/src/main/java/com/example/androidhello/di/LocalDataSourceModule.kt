package com.example.androidhello.di

import com.example.androidhello.data.source.LocalDataSource
import com.example.androidhello.data.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

}