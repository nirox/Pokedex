package com.mobgen.data.repository.service

import PokemonApiConstants
import com.mobgen.data.entity.EvolutionEntity
import com.mobgen.data.entity.PokemonSpeciesEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SpeciesService {
    @GET(PokemonApiConstants.API_POKEAPI_SPECIE)
    fun getPokemonSpecies(
        @Path("id") id: String
    ): Single<PokemonSpeciesEntity>

    @GET(PokemonApiConstants.API_POKEAPI_EVOLUTION)
    fun getPokemonEvolution(
        @Path("id") id: String
    ): Single<EvolutionEntity>
}