package com.mobgen.domain.useCase

import com.mobgen.domain.PokemonRepository
import com.mobgen.domain.Util
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager
import javax.inject.Inject

class GetPokemons @Inject constructor(private val repository: PokemonRepository) {
    companion object {
        private var offset: Long = -1L
    }

    fun execute() =
        repository.getPokemons().map { it.also { pokemons -> offset += pokemons.size } }

    fun next() = repository.getNextPokemons(offset).map { it.also { pokemons ->
        offset += pokemons.size
    } }
}