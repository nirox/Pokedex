package com.mobgen.presentation.game

import com.mobgen.domain.model.Pokemon
import com.mobgen.presentation.ViewMapper
import javax.inject.Inject

class PokemonRandomViewMapper @Inject constructor() : ViewMapper<Pokemon, GameViewModel.PokemonRandomBindView> {
    override fun map(value: Pokemon): GameViewModel.PokemonRandomBindView =
        GameViewModel.PokemonRandomBindView(
            value.name,
            value.imageUrl
        )
}