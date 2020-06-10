package dev.skymansandy.base.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dev.skymansandy.base.lifecycle.viewmodel.ViewModelProviderFactory

@Module
abstract class ViewModuleFactoryModule {

    @Binds
    abstract fun providesViewModelFactory(
        viewModelFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}