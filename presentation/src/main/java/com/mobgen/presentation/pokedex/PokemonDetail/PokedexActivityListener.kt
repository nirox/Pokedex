package com.mobgen.presentation.pokedex.pokemonDetail

interface PokedexActivityListener {
    fun onBack()
    fun goToPokemonDetail(id: Long)
    fun goToPokemonAr(id: Long, mode: String = "")
}