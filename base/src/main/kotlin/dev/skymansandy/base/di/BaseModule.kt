package dev.skymansandy.base.di

import dagger.Module

@Module(
    includes = [
        ViewModuleFactoryModule::class
    ]
)
class BaseModule