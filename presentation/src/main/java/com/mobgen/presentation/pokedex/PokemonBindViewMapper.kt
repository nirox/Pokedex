package com.mobgen.presentation.pokedex

import com.mobgen.domain.model.Pokemon
import com.mobgen.presentation.ViewMapper
import javax.inject.Inject

class PokemonBindViewMapper @Inject constructor() : ViewMapper<Pokemon, PokedexViewModel.PokemonBindView> {
    override fun map(value: Pokemon): PokedexViewModel.PokemonBindView {
        return PokedexViewModel.PokemonBindView(value.id, value.name.capitalize(), value.imageUrl)
    }
}