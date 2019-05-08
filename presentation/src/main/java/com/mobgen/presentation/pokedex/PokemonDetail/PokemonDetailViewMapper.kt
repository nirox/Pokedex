package com.mobgen.presentation.pokedex.pokemonDetail

import com.mobgen.domain.model.PokemonDetails
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewMapper
import javax.inject.Inject

class PokemonDetailViewMapper @Inject constructor(private val typeViewMapper: TypeViewMapper) :
    ViewMapper<PokemonDetails, PokemonDetailViewModel.PokemonDetailBindView> {
    override fun map(value: PokemonDetails): PokemonDetailViewModel.PokemonDetailBindView {
        return PokemonDetailViewModel.PokemonDetailBindView(
            value.name,
            value.imageUrl,
            value.description,
            value.types.map(typeViewMapper::map),
            value.evolutions.map { Pair(it.name, it.imageUrl) })
    }

    class TypeViewMapper @Inject constructor() : ViewMapper<PokemonDetails.Type, Pair<String, Int>> {
        override fun map(value: PokemonDetails.Type): Pair<String, Int> = Pair(
            value.tName.capitalize(),
            when (value) {
                PokemonDetails.Type.Bug -> R.drawable.bug
                PokemonDetails.Type.Dark -> R.drawable.dark
                PokemonDetails.Type.Dragon -> R.drawable.dragon
                PokemonDetails.Type.Electric -> R.drawable.electric
                PokemonDetails.Type.Fairy -> R.drawable.fairy
                PokemonDetails.Type.Fighting -> R.drawable.fighting
                PokemonDetails.Type.Fire -> R.drawable.fire
                PokemonDetails.Type.Flying -> R.drawable.flying
                PokemonDetails.Type.Ghost -> R.drawable.ghost
                PokemonDetails.Type.Grass -> R.drawable.grass
                PokemonDetails.Type.Ground -> R.drawable.ground
                PokemonDetails.Type.Ice -> R.drawable.ice
                PokemonDetails.Type.Normal -> R.drawable.normal
                PokemonDetails.Type.Poison -> R.drawable.poison
                PokemonDetails.Type.Psychic -> R.drawable.psychic
                PokemonDetails.Type.Rock -> R.drawable.rock
                PokemonDetails.Type.Steel -> R.drawable.steel
                else -> R.drawable.water
            }
        )

    }
}