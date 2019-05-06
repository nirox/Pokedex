package com.mobgen.presentation.pokedex.pokemonDetail

import com.mobgen.presentation.BaseViewModel

class PokemonDetailViewModel : BaseViewModel<PokemonDetailViewModel.PokemonDetailViewData>() {

    private var mainViewData =
        PokemonDetailViewData(Status.LOADING)

    init {
        data.value = mainViewData
    }

    fun getPokemonById(long: Long) {
        //TODO execute here usecase
    }

    class PokemonDetailViewData(
        override var status: Status,
        val pokemon: PokemonDetailBindView? = null
    ) : Data

    class PokemonDetailBindView(
        val name: String,
        val image: String,
        val description: String,
        val type: List<Pair<String, Int>>,
        val evolutions: List<Pair<String, String>>
    )
}
