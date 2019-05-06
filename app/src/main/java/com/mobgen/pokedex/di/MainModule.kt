package com.mobgen.pokedex.di

import android.content.Context
import com.mobgen.pokedex.PokedexApplication
import com.mobgen.presentation.game.GameActivity
import com.mobgen.presentation.launch.LaunchActivity
import com.mobgen.presentation.pokedex.PokedexActivity
import com.mobgen.presentation.pokedex.pokemonDetail.PokemonDetailFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @Binds
    abstract fun provideApplicationContext(application: PokedexApplication): Context

    @ContributesAndroidInjector
    abstract fun get(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun gameActity(): GameActivity

    @ContributesAndroidInjector
    abstract fun pokedexActivity(): PokedexActivity

    @ContributesAndroidInjector
    abstract fun pokemonDetailFragment(): PokemonDetailFragment
}