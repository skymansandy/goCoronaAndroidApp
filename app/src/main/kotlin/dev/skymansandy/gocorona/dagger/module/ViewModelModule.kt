package dev.skymansandy.gocorona.dagger.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.skymansandy.base.di.ViewModelKey
import dev.skymansandy.gocorona.presentation.home.HomeViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindStudentBriefViewModel(vm: HomeViewModel): ViewModel
}