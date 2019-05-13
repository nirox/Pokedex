package com.mobgen.pokedex.di

import com.mobgen.domain.useCase.GetPokemonDetails
import com.mobgen.domain.useCase.GetPokemons
import com.mobgen.domain.useCase.GetRandomPokemons
import org.koin.dsl.module

val domainModule = module {
    single { GetPokemonDetails(get()) }
    single { GetPokemons(get()) }
    single { GetRandomPokemons(get()) }
}