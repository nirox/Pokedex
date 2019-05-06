package com.mobgen.domain

import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        private val random = Random()
        const val MAX_POKEMON_ID: Int = 151

        fun getDate(date: String, format: String): Date {
            return SimpleDateFormat(format, Locale.ENGLISH).apply { isLenient = true }.parse(date)

        }

        fun getRandomPokemon(): Int {
            return random.nextInt(MAX_POKEMON_ID) + 1
        }
    }
}