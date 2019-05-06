package com.mobgen.data.entity

data class TypesEntity(
    val type: TypeEntity
) {
    data class TypeEntity(
        val url: String,
        val name: String
    )
}
