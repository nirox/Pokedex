package com.mobgen.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mobgen.domain.useCase.GetPokemons
import com.mobgen.presentation.game.GameViewModel
import com.mobgen.presentation.pokedex.PokedexViewModel
import com.mobgen.presentation.pokedex.PokemonBindViewMapper
import com.mobgen.presentation.pokedex.pokemonDetail.PokemonDetailViewModel
import javax.inject.Inject

class ViewModelFactory
@Inject constructor(
    private val getPokemons: GetPokemons,
    private val pokemonBindViewMapper: PokemonBindViewMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GameViewModel::class.java) -> GameViewModel()
            modelClass.isAssignableFrom(PokedexViewModel::class.java) -> PokedexViewModel(
                getPokemons,
                pokemonBindViewMapper
            )
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> PokemonDetailViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }

}