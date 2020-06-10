package dev.skymansandy.gocorona.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.skymansandy.base.di.BaseModule
import dev.skymansandy.gocorona.presentation.MainActivity
import dev.skymansandy.gocorona.presentation.home.HomeFragment

@Module(
    includes = [
        BaseModule::class,
        ViewModelModule::class
    ]
)
abstract class UiInjectorModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}