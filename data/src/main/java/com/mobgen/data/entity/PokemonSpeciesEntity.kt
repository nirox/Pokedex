package com.mobgen.data.entity

data class PokemonSpeciesEntity(
    val evolution_chain: EvolutionChainEntity,
    val flavor_text_entries: ArrayList<Flavor_text_entries>
) {
    data class Flavor_text_entries(
        val flavor_text: String,
        val language: Language,
        val version: Version
    )

    data class Language(
        val name: String
    )

    data class Version(
        val name: String
    )
}
