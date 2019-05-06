package com.mobgen.data.repository

import com.mobgen.data.mapper.PokemonDataMapper
import com.mobgen.data.mapper.PokemonDetailsDataMapper
import com.mobgen.data.mapper.Util
import com.mobgen.data.repository.service.PokemonService
import com.mobgen.data.repository.service.SpeciesService
import com.mobgen.domain.PokemonRepository
import com.mobgen.domain.model.Pokemon
import com.mobgen.domain.model.PokemonDetails
import com.mobgen.domain.subscribe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.mobgen.domain.Util as UtilDomain

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val speciesService: SpeciesService,
    private val pokemonDataMapper: PokemonDataMapper,
    private val pokemonDetailsDataMapper: PokemonDetailsDataMapper

) : PokemonRepository {
    override fun getPokemons(): Single<List<Pokemon>> =
        pokemonService.getPokemonList().map { pokemonDataMapper.map(it.results) }

    override fun getNextPokemons(offset: Long): Single<List<Pokemon>> =
        pokemonService.getPokemonList(offset = offset.toString()).map { pokemonDataMapper.map(it.results) }

    override fun getPokemonDetails(id: String) = Single.create<PokemonDetails> { emitter ->
        pokemonService.getPokemonDetails(id).subscribe(
            executor = Schedulers.computation(),
            onError = {
                emitter.onError(it)
            },
            onSuccess = { detailsRequestEntity ->
                speciesService.getPokemonSpecies(Util.getId(detailsRequestEntity.species.url)).subscribe(
                    executor = Schedulers.computation(),
                    onError = {
                        emitter.onError(it)
                    },
                    onSuccess = { pokemonSpeciesEntity ->
                        speciesService.getPokemonEvolution(Util.getId(pokemonSpeciesEntity.evolution_chain.url))
                            .subscribe(
                                executor = Schedulers.computation(),
                                onError = {
                                    emitter.onError(it)
                                },
                                onSuccess = { evolutionEntity ->
                                    emitter.onSuccess(
                                        pokemonDetailsDataMapper.map(
                                            detailsRequestEntity,
                                            evolutionEntity,
                                            pokemonSpeciesEntity
                                        )
                                    )
                                }
                            )
                    }
                )
            }
        )
    }

    override fun getRandomPokemons(number: Int): Single<List<Pokemon>> = Single.create { emitter ->
        var numberIds = number
        var id: Int
        var wait = false
        val pokemons: ArrayList<Pokemon> = ArrayList()

        while (numberIds > 0) {
            if (!wait) {
                id = UtilDomain.getRandomPokemon()
                wait = true
                numberIds--
                pokemonService.getPokemonDetails(id.toString()).subscribe(
                    executor = Schedulers.computation(),
                    onError = {
                        emitter.onError(it)
                    },
                    onSuccess = { detailsRequestEntity ->
                        wait = false
                        pokemons.add(
                            pokemonDetailsDataMapper.map(
                                detailsRequestEntity
                            )
                        )

                    }
                )
            }
        }

        emitter.onSuccess(pokemons)
    }

}