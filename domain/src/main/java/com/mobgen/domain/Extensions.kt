package com.mobgen.domain

fun <T> T?.check(ifNotNull: (T) -> Unit, ifNull: () -> Unit = {}) {
    if (this == null) {
        ifNull()
    } else {
        ifNotNull(this)
    }
}

fun String.removeLineBreak() = this.replace(regex = Regex("[\n]"), replacement = " ")