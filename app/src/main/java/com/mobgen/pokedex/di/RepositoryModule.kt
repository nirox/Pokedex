package com.mobgen.pokedex.di

import com.mobgen.data.repository.PokemonRepositoryImpl
import com.mobgen.domain.PokemonRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun providePokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository

}