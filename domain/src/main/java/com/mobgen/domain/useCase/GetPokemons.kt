package com.mobgen.domain.useCase

import com.mobgen.domain.PokemonRepository

class GetPokemons (private val repository: PokemonRepository) {
    companion object {
        private var offset: Long = -1L
    }

    fun execute() =
        repository.getPokemons().map { it.also { pokemons -> offset = pokemons.size.toLong() } }

    fun next() = repository.getNextPokemons(offset).map {
        it.also { pokemons ->
            offset += pokemons.size
        }
    }
}