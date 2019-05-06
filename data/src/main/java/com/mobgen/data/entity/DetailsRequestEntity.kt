package com.mobgen.data.entity

data class DetailsRequestEntity(
    val id: Long,
    val name: String,
    val height: Int,
    val weight: Int,
    val species: SpeciesEntity,
    val types: ArrayList<TypesEntity>
)