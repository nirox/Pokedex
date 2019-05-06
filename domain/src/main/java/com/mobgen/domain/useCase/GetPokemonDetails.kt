package com.mobgen.domain.useCase

import com.mobgen.domain.PokemonRepository
import javax.inject.Inject

class GetPokemonDetails @Inject constructor(private val repository: PokemonRepository) {
    fun execute(id: String) = repository.getPokemonDetails(id).map { it }
}