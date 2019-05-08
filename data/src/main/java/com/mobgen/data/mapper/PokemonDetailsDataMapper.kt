package com.mobgen.data.mapper

import PokemonApiConstants
import com.mobgen.data.entity.DetailsRequestEntity
import com.mobgen.data.entity.EvolutionEntity
import com.mobgen.data.entity.PokemonSpeciesEntity
import com.mobgen.domain.model.Pokemon
import com.mobgen.domain.model.PokemonDetails
import com.mobgen.domain.removeLineBreak
import javax.inject.Inject

class PokemonDetailsDataMapper @Inject constructor(private val evolutionsDataMapper: EvolutionsDataMapper) {
    fun map(detail: DetailsRequestEntity, evolution: EvolutionEntity, species: PokemonSpeciesEntity): PokemonDetails {
        return PokemonDetails(detail.id,
            detail.name,
            species.flavor_text_entries.first { it.language.name == PokemonApiConstants.DEFAULT_LANGUAGE }.flavor_text.removeLineBreak(),
            String.format(PokemonApiConstants.IMAGE_URL, detail.id),
            detail.height.toString(),
            detail.weight.toString(),
            evolutionsDataMapper.map(evolution),
            detail.types.map { PokemonDetails.Type.values().first { type -> type.tName == it.type.name } })
    }

    fun map(detail: DetailsRequestEntity) =
        Pokemon(detail.id, detail.name, String.format(PokemonApiConstants.IMAGE_URL, detail.id))
}