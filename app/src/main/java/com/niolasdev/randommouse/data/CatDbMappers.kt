package com.niolasdev.randommouse.data

import com.niolasdev.storage.model.BreedDbo
import com.niolasdev.storage.model.CatDbo

class CatDboMapper(
    val breedDboMapper: BreedDboMapper,
): Mapper<CatDbo, Cat> {
    override fun from(source: CatDbo): Cat {
        return Cat(
            id = "", //source.catId,
            url = "",//source.cat.url,
            breeds = breedDboMapper.from(/*source.breeds*/emptyList())
        )
    }

}

class BreedDboMapper : ListMapper<BreedDbo, Breed> {

    override fun from(sources: List<BreedDbo>?): List<Breed> {
        return sources?.map {
            Breed(
                id = it.breedId,
                name = it.name,
                temperament = it.temperament,
            )
        } ?: emptyList()
    }
}