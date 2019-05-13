package com.mobgen.data.mapper

import PokemonApiConstants
import com.mobgen.data.entity.PokemonEntity
import com.mobgen.domain.model.Pokemon

class PokemonDataMapper  : DataMapper<PokemonEntity, Pokemon> {
    override fun map(value: PokemonEntity): Pokemon? {
        val id = Util.getId(value.url).toLong()
        return if (id <= PokemonApiConstants.MAX_POKEMON_LIMIT) Pokemon(
            id,
            value.name,
            String.format(PokemonApiConstants.IMAGE_URL, id)
        ) else null
    }

}