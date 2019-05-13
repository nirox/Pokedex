package com.mobgen.pokedex

import android.app.Application
import com.mobgen.pokedex.di.dataModule
import com.mobgen.pokedex.di.domainModule
import com.mobgen.pokedex.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokedexApplication)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }

}