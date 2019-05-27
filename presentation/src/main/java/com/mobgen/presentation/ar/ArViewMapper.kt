package com.mobgen.presentation.ar

import com.mobgen.domain.model.PokemonDetails
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewMapper

class ArViewMapper(
    private val typeViewMapper: TypeViewMapper
) :
    ViewMapper<PokemonDetails, ArViewModel.ArBindView> {
    override fun map(value: PokemonDetails): ArViewModel.ArBindView {
        return ArViewModel.ArBindView(
            value.name,
            value.imageUrl,
            Util3d.Pokemon.values().firstOrNull { it.pName == value.name }?.design ?: -1,
            value.description,
            value.types.map(typeViewMapper::map)
        )
    }

    class TypeViewMapper : ViewMapper<PokemonDetails.Type, Pair<String, Int>> {
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