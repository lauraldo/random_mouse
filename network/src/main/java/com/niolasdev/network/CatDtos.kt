package com.niolasdev.network

import kotlinx.serialization.Serializable

@Serializable
class CatDto(
    val id: String,
    val url: String,
    val breeds: List<BreedDto>? = null
)

@Serializable
class BreedDto(
    val id: String,
    val name: String,
    val temperament: String?,
    val description: String?,
    val origin: String?,
    val country_code: String?,
    val life_span: String?,
    val weight: WeightDto?,
    val height: HeightDto?
)

@Serializable
class WeightDto(
    val imperial: String?,
    val metric: String?
)

@Serializable
class HeightDto(
    val imperial: String?,
    val metric: String?
)