package dev.skymansandy.gocorona.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.skymansandy.gocorona.GoCoronaApp
import dev.skymansandy.gocorona.di.module.AppModule
import dev.skymansandy.gocorona.di.module.DbModule
import dev.skymansandy.gocorona.di.module.NetworkModule
import dev.skymansandy.gocorona.di.module.UiInjectorModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        UiInjectorModule::class,
        DbModule::class,
        NetworkModule::class,
        AppModule::class]
)
interface AppComponent : AndroidInjector<GoCoronaApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}