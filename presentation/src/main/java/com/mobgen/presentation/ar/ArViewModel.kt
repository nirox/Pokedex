package com.mobgen.presentation.ar

import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetPokemonDetails
import com.mobgen.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class ArViewModel(
    private val getPokemonDetails: GetPokemonDetails,
    private val viewMapper: ArViewMapper
) :
    BaseViewModel<ArViewModel.PokemonDetailViewData>() {
    private var pokemonDetailsBind: ArBindView? = null
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
                        ar = pokemonDetailsBind
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
        var ar: ArBindView? = null
    ) : Data

    class ArBindView(
        val name: String,
        val image: String,
        val design3d: Int,
        val description: String,
        val type: List<Pair<String, Int>>
    )
}
