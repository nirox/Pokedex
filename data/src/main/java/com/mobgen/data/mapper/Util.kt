package com.mobgen.data.mapper

class Util {
    companion object {
        fun getId(url: String): String {
            val splitted = url.split("/").toMutableList()
            splitted.removeAt(splitted.size - 1)
            return splitted.last()
        }
    }
}