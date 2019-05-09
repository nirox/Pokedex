package com.mobgen.presentation.pokedex

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetPokemons
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class PokedexViewModel(private val getPokemons: GetPokemons, private val pokemonBindViewMapper: PokemonBindViewMapper) :
    BaseViewModel<PokedexViewModel.PokedexViewData>() {
    private var pokedexViewData =
        PokedexViewData(Status.LOADING)
    private var _pokemons = mutableListOf<PokemonBindView>()

    init {
        data.value = pokedexViewData
    }

    fun getPokemons() {
        if (_pokemons.isEmpty()) {
            getFirstPage()
        } else {
            getLastPage()
        }
    }

    private fun getFirstPage() {
        data.value = pokedexViewData.apply { status = Status.LOADING }
        executeUseCase {
            getPokemons.execute().subscribe(
                executor = AndroidSchedulers.mainThread(),
                onSuccess = {
                    _pokemons.addAll(it.map(pokemonBindViewMapper::map))
                    data.postValue(pokedexViewData.apply {
                        pokemons = _pokemons
                        status = Status.SUCCESS
                    })
                },
                onError = {
                    data.postValue(pokedexViewData.apply {
                        status = Status.ERROR
                    })
                }
            )
        }
    }

    private fun getLastPage() {
        if (pokedexViewData.status != Status.LOADING) {
            data.value = pokedexViewData.apply { status = Status.LOADING }
            executeUseCase {
                getPokemons.next().subscribe(
                    executor = AndroidSchedulers.mainThread(),
                    onSuccess = {
                        _pokemons.addAll(it.map(pokemonBindViewMapper::map))
                        data.postValue(pokedexViewData.apply {
                            pokemons = _pokemons
                            status = Status.SUCCESS
                        })

                    },
                    onError = {
                        data.postValue(pokedexViewData.apply {
                            status = Status.ERROR
                        })
                    }
                )
            }
        }
    }

    class PokedexViewData(
        override var status: Status,
        var pokemons: List<PokemonBindView> = listOf()
    ) : Data

    class PokemonBindView(
        val id: Long = -1,
        val name: String = "",
        val image: String = ""
    )
}
