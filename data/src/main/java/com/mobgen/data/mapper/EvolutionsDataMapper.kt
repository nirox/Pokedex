package com.mobgen.data.mapper

import PokemonApiConstants
import com.mobgen.data.entity.EvolutionEntity
import com.mobgen.data.entity.EvolveToEntity
import com.mobgen.domain.model.Pokemon

class EvolutionsDataMapper : DataMapper<EvolutionEntity, List<Pokemon>> {
    override fun map(value: EvolutionEntity): List<Pokemon> {
        val pokemonsMutable = mutableListOf<Pokemon>()
        var id = Util.getId(value.chain.species.url).toLong()
        pokemonsMutable.add(
            Pokemon(
                id,
                value.chain.species.name,
                String.format(PokemonApiConstants.IMAGE_URL, id)
            )
        )

        var evolutions: ArrayList<EvolveToEntity> = value.chain.evolves_to
        var finish = false

        while (!finish) {
            if (evolutions.isNotEmpty()) {
                id = Util.getId(evolutions.first().species.url).toLong()
                pokemonsMutable.add(
                    Pokemon(
                        id,
                        evolutions.first().species.name,
                        String.format(PokemonApiConstants.IMAGE_URL, id)
                    )
                )

                evolutions = evolutions.first().evolves_to
            } else {
                finish = true
            }
        }

        return pokemonsMutable.toList()
    }
}