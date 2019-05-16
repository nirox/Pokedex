package com.mobgen.pokedex.di

import com.mobgen.presentation.game.GameViewModel
import com.mobgen.presentation.game.PokemonRandomViewMapper
import com.mobgen.presentation.pokedex.PokedexViewModel
import com.mobgen.presentation.pokedex.PokemonBindViewMapper
import com.mobgen.presentation.pokedex.pokemonDetail.PokemonDetailViewMapper
import com.mobgen.presentation.pokedex.pokemonDetail.PokemonDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { PokemonRandomViewMapper() }
    viewModel { GameViewModel(get(), get()) }
    single { PokemonBindViewMapper() }
    viewModel { PokedexViewModel(get(), get()) }
    single { PokemonDetailViewMapper.TypeViewMapper() }
    single { PokemonDetailViewMapper.PokemonViewMapper() }
    single { PokemonDetailViewMapper(get(), get()) }
    viewModel { PokemonDetailViewModel(get(), get()) }
}