package com.mobgen.presentation.game

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetRandomPokemons
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*


class GameViewModel(
    private val getRandomPokemon: GetRandomPokemons,
    private val pokemonRandomMapper: PokemonRandomViewMapper
) :
    BaseViewModel<GameViewModel.GameViewData>() {
    private var mainViewData =
        GameViewData(Status.SUCCESS)
    private var _pokemons = mutableListOf<PokemonRandomBindView>()
    private var noPosibleIds = mutableListOf<Long>()

    init {
        data.value = mainViewData
    }

    fun getPokemon() {
        if (data.value?.status == Status.SUCCESS) {
            data.value = mainViewData.apply { status = Status.LOADING }
            executeUseCase {
                getRandomPokemon.execute(GameActivity.POKEMON_GENERATE_PER_ROUND, noPosibleIds).subscribe(
                    executor = AndroidSchedulers.mainThread(),
                    onSuccess = { pokemonList ->
                        noPosibleIds.add(pokemonList.first().id)
                        _pokemons.clear()
                        _pokemons.addAll(pokemonList.map(pokemonRandomMapper::map))
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                data.postValue(mainViewData.apply {
                                    randomPokemons = _pokemons
                                    status = Status.SUCCESS
                                })
                            }

                        }, GameActivity.TIME_TO_RESET)

                    },
                    onError = {
                        throw it
                        data.postValue(mainViewData.apply {
                            status = Status.ERROR
                        })
                    }
                )
            }
        }
    }

    class GameViewData(
        override var status: Status,
        var randomPokemons: List<PokemonRandomBindView> = listOf()
    ) : Data

    class PokemonRandomBindView(
        val name: String,
        val image: String
    )
}
