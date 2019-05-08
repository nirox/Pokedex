package com.mobgen.domain

import java.util.*

class Util {
    companion object {
        private val random = Random()
        private const val MAX_POKEMON_GAME_ID: Int = 807
        fun getDate(date: String, format: String): Date {
            return SimpleDateFormat(format, Locale.ENGLISH).apply { isLenient = true }.parse(date)

        }

        fun getRandomPokemon(): Int {
            return random.nextInt(MAX_POKEMON_GAME_ID) + 1
        }
    }
}