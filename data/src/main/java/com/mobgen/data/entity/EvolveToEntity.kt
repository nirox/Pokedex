package com.mobgen.data.entity

data class EvolveToEntity(
    val species: EvolutionSpeciesEntity,
    val evolves_to: ArrayList<EvolveToEntity>
)
