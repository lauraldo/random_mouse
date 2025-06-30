package com.niolasdev.randommouse.data

import com.niolasdev.network.BreedDto
import com.niolasdev.network.CatDto
import com.niolasdev.network.FLAG_API_BASE
import com.niolasdev.storage.model.BreedDbo
import com.niolasdev.storage.model.CatDbo
import com.niolasdev.storage.model.HeightDbo
import com.niolasdev.storage.model.WeightDbo

class CatDboMapper(
    val breedDboMapper: BreedDboMapper,
) : Mapper<CatDbo, Cat> {
    override fun from(source: CatDbo): Cat {
        return Cat(
            id = source.catId,
            url = source.url,
            breeds = breedDboMapper.from(source.breeds)
        )
    }
}

class BreedDboMapper : ListMapper<BreedDbo, Breed> {

    override fun from(sources: List<BreedDbo>?): List<Breed> {
        return sources?.map {
            Breed(
                id = it.id,
                name = it.name,
                description = it.description,
                temperament = it.temperament,
                origin = it.origin,
                countryFlagUrl = "${FLAG_API_BASE}${it.countryCode}.svg",
            )
        } ?: emptyList()
    }
}

class CatDataMapper(
    val breedDataMapper: BreedDataMapper,
) : Mapper<CatDto, CatDbo> {
    override fun from(source: CatDto): CatDbo {
        return CatDbo(
            catId = source.id,
            url = source.url,
            breeds = breedDataMapper.from(source.breeds)
        )
    }

}

class BreedDataMapper : ListMapper<BreedDto, BreedDbo> {

    override fun from(sources: List<BreedDto>?): List<BreedDbo> {
        return sources?.map {
            BreedDbo(
                id = it.id,
                name = it.name,
                temperament = it.temperament,
                lifeSpan = it.life_span,
                description = it.description,
                origin = it.origin,
                countryCode = it.country_code,
                weight = WeightDbo(
                    imperial = it.weight?.imperial,
                    metric = it.weight?.metric
                ),
                height = HeightDbo(
                    imperial = it.height?.imperial,
                    metric = it.height?.metric,
                ),
            )
        } ?: emptyList()
    }
}