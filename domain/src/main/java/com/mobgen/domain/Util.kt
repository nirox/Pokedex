package com.mobgen.domain

import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun getDate(date: String, format: String): Date {
            return SimpleDateFormat(format, Locale.ENGLISH).apply { isLenient = true }.parse(date)
        }

    }
}