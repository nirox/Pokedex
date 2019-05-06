package com.mobgen.domain

import com.mobgen.domain.model.Pokemon
import com.mobgen.domain.model.PokemonDetails
import io.reactivex.Single

interface PokemonRepository {
    fun getPokemons(): Single<List<Pokemon>>
    fun getNextPokemons(offset: Long): Single<List<Pokemon>>
    fun getPokemonDetails(id: String): Single<PokemonDetails>
    fun getRandomPokemons(number: Int): Single<List<Pokemon>>
}