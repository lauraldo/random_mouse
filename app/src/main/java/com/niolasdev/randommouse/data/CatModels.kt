package com.niolasdev.randommouse.data

class Cat(
    val id: String,
    val url: String,
    val breeds: List<Breed>? = null
) {
    override fun toString(): String {
        return "^_^ $id; [${breeds}]"
    }
}

class Breed(
    val id: String,
    val name: String,
    val temperament: String?,
) {
    override fun toString(): String {
        return "$id; $name; $temperament"
    }
}