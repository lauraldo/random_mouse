package com.niolasdev.randommouse.data

import com.niolasdev.network.BreedDto
import com.niolasdev.network.CatDto
import com.niolasdev.network.FLAG_API_BASE

interface Mapper<K, V> {
    fun from(source: K): V
}

interface ListMapper<K, V> {
    fun from(sources: List<K>?): List<V>
}

class CatMapper(
    val breedMapper: BreedMapper,
): Mapper<CatDto, Cat> {

    override fun from(source: CatDto): Cat {
        return Cat(
            id = source.id,
            url = source.url,
            breeds = breedMapper.from(source.breeds)
        )
    }
}

class BreedMapper: ListMapper<BreedDto, Breed> {

    override fun from(sources: List<BreedDto>?): List<Breed> {
        return sources?.map {
            Breed(
                id = it.id,
                name = it.name,
                description = it.description,
                temperament = it.temperament,
                origin = it.origin,
                countryFlagUrl = "${FLAG_API_BASE}${it.country_code}.svg"
            )
        } ?: emptyList()
    }
}