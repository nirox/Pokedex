package com.mobgen.presentation.pokedex.pokemonDetail

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetPokemonDetails
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class PokemonDetailViewModel(
    private val getPokemonDetails: GetPokemonDetails,
    private val viewMapper: PokemonDetailViewMapper
) :
    BaseViewModel<PokemonDetailViewModel.PokemonDetailViewData>() {
    private var pokemonDetailsBind: PokemonDetailBindView? = null
    private var mainViewData =
        PokemonDetailViewData(Status.LOADING, pokemonDetailsBind)

    init {
        data.value = mainViewData
    }

    fun getPokemonById(id: Long) {
        executeUseCase {
            getPokemonDetails.execute(id.toString()).subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = { pokemonDetails ->
                    pokemonDetailsBind = viewMapper.map(pokemonDetails)
                    data.postValue(mainViewData.apply {
                        status = Status.SUCCESS
                        pokemon = pokemonDetailsBind
                    })
                },
                onError = {
                    data.postValue(mainViewData.apply {
                        status = Status.ERROR
                    })
                }
            )
        }
    }

    class PokemonDetailViewData(
        override var status: Status,
        var pokemon: PokemonDetailBindView? = null
    ) : Data

    class PokemonDetailBindView(
        val name: String,
        val image: String,
        val description: String,
        val type: List<Pair<String, Int>>,
        val evolutions: List<Pair<String, String>>
    )
}
