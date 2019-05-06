package com.mobgen.data.entity

data class EvolutionEntity(
    val chain: ChainEntity
) {
    data class ChainEntity(
        val species: EvolutionSpeciesEntity,
        val evolves_to: ArrayList<EvolveToEntity>
    )
}
