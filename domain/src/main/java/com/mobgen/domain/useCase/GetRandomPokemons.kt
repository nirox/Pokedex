package com.mobgen.domain.useCase

import com.mobgen.domain.PokemonRepository
import com.mobgen.domain.model.Pokemon
import io.reactivex.Single

class GetRandomPokemons (private val repository: PokemonRepository) {
    fun execute(number: Int, noPosibleIds: List<Long>): Single<List<Pokemon>> =
        repository.getRandomPokemons(number, noPosibleIds)
}