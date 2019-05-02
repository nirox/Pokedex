package com.mobgen.domain.useCase

import com.mobgen.domain.model.Pokemon
import io.reactivex.Single

class GetPokemons {
    fun execute(): Single<List<Pokemon>> = Single.create<List<Pokemon>> { }
}