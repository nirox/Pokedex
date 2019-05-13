package com.mobgen.data.repository

import com.mobgen.data.mapper.PokemonDataMapper
import com.mobgen.data.mapper.PokemonDetailsDataMapper
import com.mobgen.data.mapper.Util
import com.mobgen.data.repository.service.PokemonService
import com.mobgen.data.repository.service.SpeciesService
import com.mobgen.domain.PokemonRepository
import com.mobgen.domain.model.Pokemon
import com.mobgen.domain.model.PokemonDetails
import io.reactivex.Single
import com.mobgen.domain.Util as UtilDomain

class PokemonRepositoryImpl(
    private val pokemonService: PokemonService,
    private val speciesService: SpeciesService,
    private val pokemonDataMapper: PokemonDataMapper,
    private val pokemonDetailsDataMapper: PokemonDetailsDataMapper

) : PokemonRepository {
    override fun getPokemons(): Single<List<Pokemon>> =
        pokemonService.getPokemonList().map { pokemonDataMapper.map(it.results) }

    override fun getNextPokemons(offset: Long) = pokemonService.getPokemonList(offset = offset.toString()).map {
        it.results.mapNotNull { pokemon ->
            pokemonDataMapper.map(pokemon)
        }
    }


    override fun getPokemonDetails(id: String): Single<PokemonDetails> =
        pokemonService.getPokemonDetails(id).flatMap { detailsRequestEntity ->
            speciesService.getPokemonSpecies(Util.getId(detailsRequestEntity.species.url))
                .flatMap { pokemonSpeciesEntity ->
                    speciesService.getPokemonEvolution(Util.getId(pokemonSpeciesEntity.evolution_chain.url))
                        .map { evolutionEntity ->
                            pokemonDetailsDataMapper.map(
                                detailsRequestEntity,
                                evolutionEntity,
                                pokemonSpeciesEntity
                            )
                        }
                }
        }

    override fun getRandomPokemons(number: Int, noPosibleIds: List<Long>) =
        randomPokemonGenerator(number, mutableListOf(), noPosibleIds, mutableListOf())

    private fun randomPokemonGenerator(
        number: Int,
        pokemons: MutableList<Pokemon>,
        noPosibleIds: List<Long>,
        generatedIds: MutableList<Long>
    ): Single<List<Pokemon>> {
        var id = UtilDomain.getRandomPokemon().toLong()
        while (generatedIds.contains(id) || noPosibleIds.contains(id)) id = UtilDomain.getRandomPokemon().toLong()
        generatedIds.add(id)
        return if (generatedIds.size == number) pokemonService.getPokemonDetails(id.toString()).map { detailsRequestEntity ->
            pokemons.add(pokemonDetailsDataMapper.map(detailsRequestEntity))
            pokemons
        }
        else pokemonService.getPokemonDetails(id.toString()).flatMap { detailsRequestEntity ->
            pokemons.add(pokemonDetailsDataMapper.map(detailsRequestEntity))
            randomPokemonGenerator(number, pokemons, noPosibleIds, generatedIds)
        }

    }
}