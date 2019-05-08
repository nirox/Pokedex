package com.mobgen.domain

import java.util.*

class Util {
    companion object {
        private val random = Random()
        const val MAX_POKEMON_ID: Int = 807

        fun getRandomPokemon(): Int {
            return random.nextInt(MAX_POKEMON_ID) + 1
        }
    }
}