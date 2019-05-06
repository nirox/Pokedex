package com.mobgen.data.entity

data class PokemonRequestEntity(
    val count: Int,
    val next: String,
    val previous: String,
    val results: ArrayList<PokemonEntity>
)