package com.mobgen.domain.model

data class PokemonDetails(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val height: String,
    val weight: String,
    val evolutions: List<Pokemon>,
    val types: List<Type>
) {
    enum class Type(val tName: String) {
        Bug("bug"),
        Dark("dark"),
        Dragon("dragon"),
        Electric("electric"),
        Fairy("fairy"),
        Fighting("fighting"),
        Fire("fire"),
        Flying("flying"),
        Ghost("ghost"),
        Grass("grass"),
        Ground("ground"),
        Ice("ice"),
        Normal("normal"),
        Poison("poison"),
        Psychic("psychic"),
        Rock("rock"),
        Steel("steel"),
        Water("water")
    }
}