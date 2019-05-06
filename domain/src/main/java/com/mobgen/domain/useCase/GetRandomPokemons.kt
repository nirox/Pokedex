package com.mobgen.domain.useCase

import com.mobgen.domain.PokemonRepository
import com.mobgen.domain.model.Pokemon
import io.reactivex.Single
import javax.inject.Inject

class GetRandomPokemons @Inject constructor(private val repository: PokemonRepository) {
    fun execute(number: Int): Single<List<Pokemon>> = repository.getRandomPokemons(number)
}