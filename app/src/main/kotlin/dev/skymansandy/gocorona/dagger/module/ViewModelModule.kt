package dev.skymansandy.gocorona.dagger.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.skymansandy.base.di.ViewModelKey
import dev.skymansandy.gocorona.presentation.about.SettingsViewModel
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryViewModel
import dev.skymansandy.gocorona.presentation.countrydata.CountryDataViewModel
import dev.skymansandy.gocorona.presentation.districtdata.DistrictDataViewModel
import dev.skymansandy.gocorona.presentation.health.HealthViewModel
import dev.skymansandy.gocorona.presentation.home.HomeViewModel
import dev.skymansandy.gocorona.presentation.main.MainViewModel
import dev.skymansandy.gocorona.presentation.splash.SplashViewModel
import dev.skymansandy.gocorona.presentation.statedata.StateDataViewModel
import dev.skymansandy.gocorona.presentation.world.WorldViewModel

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
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

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
    @ViewModelKey(HealthViewModel::class)
    abstract fun bindHealthViewModel(vm: HealthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(vm: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseCountryViewModel::class)
    abstract fun bindChooseCountryViewModel(vm: ChooseCountryViewModel): ViewModel
}