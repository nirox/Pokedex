package com.mobgen.pokedex.di

import android.content.Context
import com.mobgen.pokedex.PokedexApplication
import com.mobgen.presentation.launch.LaunchActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun get(): LaunchActivity

    @Binds
    abstract fun provideApplicationContext(application: PokedexApplication): Context
}