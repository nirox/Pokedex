package com.mobgen.data.mapper

import PokemonApiConstants
import com.mobgen.data.entity.PokemonEntity
import com.mobgen.domain.model.Pokemon
import javax.inject.Inject

class PokemonDataMapper @Inject constructor() : DataMapper<PokemonEntity, Pokemon> {
    override fun map(value: PokemonEntity): Pokemon {
        val id = Util.getId(value.url)
        return Pokemon(id.toLong(), value.name, String.format(PokemonApiConstants.IMAGE_URL, id))
    }

}