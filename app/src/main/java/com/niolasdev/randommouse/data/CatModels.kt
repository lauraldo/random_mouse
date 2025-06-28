package com.niolasdev.randommouse.data

import kotlinx.serialization.Serializable

@Serializable
class Cat(
    val id: String,
    val url: String,
    val breeds: List<Breed>? = null
) {
    override fun toString(): String {
        return "^_^ $id; [${breeds}]"
    }
}

@Serializable
class Breed(
    val id: String,
    val name: String,
    val temperament: String?,
    val description: String?,
) {
    override fun toString(): String {
        return "$id; $name; $temperament"
    }
}