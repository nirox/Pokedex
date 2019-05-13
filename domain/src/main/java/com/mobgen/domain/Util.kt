package com.mobgen.domain

import java.util.*

object Util {

        private val random = Random()
        private const val MAX_POKEMON_GAME_ID: Int = 807
        fun getRandomPokemon(): Int {
            return random.nextInt(MAX_POKEMON_GAME_ID) + 1
        }

}