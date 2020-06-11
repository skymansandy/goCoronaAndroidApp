package dev.skymansandy.gocorona.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.skymansandy.base.di.BaseModule
import dev.skymansandy.gocorona.presentation.health.HealthFragment
import dev.skymansandy.gocorona.presentation.home.HomeFragment
import dev.skymansandy.gocorona.presentation.main.MainActivity
import dev.skymansandy.gocorona.presentation.news.NewsFragment
import dev.skymansandy.gocorona.presentation.settings.SettingsFragment
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
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeHealthFragment(): HealthFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}