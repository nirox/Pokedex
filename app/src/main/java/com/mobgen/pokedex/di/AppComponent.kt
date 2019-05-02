package com.mobgen.pokedex.di

import com.mobgen.pokedex.PokedexApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        MainModule::class,
        ServicesModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<PokedexApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PokedexApplication>()
}