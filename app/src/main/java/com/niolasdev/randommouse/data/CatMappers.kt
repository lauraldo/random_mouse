package com.niolasdev.randommouse.data

import com.niolasdev.network.BreedDto
import com.niolasdev.network.CatDto

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

    override fun from(dto: List<BreedDto>?): List<Breed> {
        return dto?.map {
            Breed(
                id = it.id,
                name = it.name,
                temperament = it.temperament,
            )
        } ?: emptyList()
    }
}