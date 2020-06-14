package dev.skymansandy.gocorona.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.skymansandy.base.di.BaseModule
import dev.skymansandy.gocorona.presentation.main.MainActivity
import dev.skymansandy.gocorona.presentation.main.about.AboutFragment
import dev.skymansandy.gocorona.presentation.main.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.main.world.country.CountryDataFragment
import dev.skymansandy.gocorona.presentation.main.india.district.DistrictDataFragment
import dev.skymansandy.gocorona.presentation.main.india.IndiaFragment
import dev.skymansandy.gocorona.presentation.main.india.state.StateDataFragment
import dev.skymansandy.gocorona.presentation.main.world.WorldFragment
import dev.skymansandy.gocorona.presentation.splash.SplashActivity

@Module(
    includes = [
        BaseModule::class,
        ViewModelModule::class
    ]
)
abstract class UiInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity


    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): IndiaFragment

    @ContributesAndroidInjector
    abstract fun contributeStateDataFragment(): StateDataFragment

    @ContributesAndroidInjector
    abstract fun contributeDistrictDataFragment(): DistrictDataFragment

    @ContributesAndroidInjector
    abstract fun contributeCountryDataFragment(): CountryDataFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): WorldFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): AboutFragment

    @ContributesAndroidInjector
    abstract fun contributeChooseCountryBottomSheet(): ChooseCountryBottomSheet
}