package com.mobgen.data.repository.service

import PokemonApiConstants
import com.mobgen.data.entity.DetailsRequestEntity
import com.mobgen.data.entity.PokemonRequestEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET(PokemonApiConstants.API_POKEAPI_POKEMON)
    fun getPokemonList(
        @Query("offset") offset: String? = null,
        @Query("limit") limit: String? = PokemonApiConstants.LIMIT.toString()
    ): Single<PokemonRequestEntity>

    @GET(PokemonApiConstants.API_POKEAPI_POKEMON_DETAIL)
    fun getPokemonDetails(
        @Path("id") id: String
    ): Single<DetailsRequestEntity>
}