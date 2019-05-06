package com.mobgen.domain.model

data class PokemonDetails(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val height: String,
    val weight: String,
    val evolutions: List<Pokemon>,
    val types: List<String>
)