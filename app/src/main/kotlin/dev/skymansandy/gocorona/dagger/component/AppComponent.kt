package dev.skymansandy.gocorona.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.skymansandy.base.di.AppScope
import dev.skymansandy.gocorona.GoCoronaApp
import dev.skymansandy.gocorona.dagger.module.AppModule
import dev.skymansandy.gocorona.dagger.module.UiInjectorModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        UiInjectorModule::class,
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