package com.mobgen.domain.useCase

import com.mobgen.domain.PokemonRepository

class GetPokemonDetails (private val repository: PokemonRepository) {
    fun execute(id: String) = repository.getPokemonDetails(id).map { it }
}