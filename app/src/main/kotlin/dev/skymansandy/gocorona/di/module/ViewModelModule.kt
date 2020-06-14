package dev.skymansandy.gocorona.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.skymansandy.base.di.ViewModelKey
import dev.skymansandy.gocorona.presentation.main.MainViewModel
import dev.skymansandy.gocorona.presentation.main.about.AboutViewModel
import dev.skymansandy.gocorona.presentation.main.choosecountry.ChooseCountryViewModel
import dev.skymansandy.gocorona.presentation.main.world.country.CountryDataViewModel
import dev.skymansandy.gocorona.presentation.main.india.district.DistrictDataViewModel
import dev.skymansandy.gocorona.presentation.main.india.IndiaViewModel
import dev.skymansandy.gocorona.presentation.main.india.state.StateDataViewModel
import dev.skymansandy.gocorona.presentation.main.world.WorldViewModel
import dev.skymansandy.gocorona.presentation.splash.SplashViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(vm: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(vm: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IndiaViewModel::class)
    abstract fun bindHomeViewModel(vm: IndiaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StateDataViewModel::class)
    abstract fun bindStateDataViewModel(vm: StateDataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DistrictDataViewModel::class)
    abstract fun bindDistrictDataViewModel(vm: DistrictDataViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CountryDataViewModel::class)
    abstract fun bindCountryDataViewModel(vm: CountryDataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorldViewModel::class)
    abstract fun bindNewsViewModel(vm: WorldViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindSettingsViewModel(vm: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseCountryViewModel::class)
    abstract fun bindChooseCountryViewModel(vm: ChooseCountryViewModel): ViewModel
}