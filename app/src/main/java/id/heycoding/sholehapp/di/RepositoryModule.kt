package id.heycoding.sholehapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.heycoding.sholehapp.data.SholehRepositoryImpl
import id.heycoding.sholehapp.domain.repository.SholehRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideSholehRepository(sholehRepositoryImpl: SholehRepositoryImpl): SholehRepository
}