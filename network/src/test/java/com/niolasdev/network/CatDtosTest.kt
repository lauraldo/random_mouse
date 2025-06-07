package com.niolasdev.network

import org.junit.Test
import org.junit.Assert.assertEquals
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNull

class CatDtosTest {

    private val json = Json { prettyPrint = true }

    @Test
    fun `test CatDto serialization and deserialization`() {
        val cat = CatDto(
            id = "abc123",
            url = "https://example.com/cat.jpg",
            breeds = listOf(
                BreedDto(
                    id = "b1",
                    name = "Siamese",
                    temperament = "Friendly",
                    life_span = "12-15",
                    weight = WeightDto("6-10", "3-5"),
                    height = HeightDto("9-11", "23-28")
                )
            )
        )

        val jsonString = json.encodeToString(CatDto.serializer(), cat)
        val parsedCat = json.decodeFromString<CatDto>(jsonString)

        assertEquals(cat.id, parsedCat.id)
        assertEquals(cat.url, parsedCat.url)
        assertEquals(cat.breeds?.get(0)?.id, parsedCat.breeds?.get(0)?.id)
        assertEquals(cat.breeds?.get(0)?.name, parsedCat.breeds?.get(0)?.name)
        assertEquals(cat.breeds?.get(0)?.life_span, parsedCat.breeds?.get(0)?.life_span)
    }

    @Test
    fun `test CatDto with null breeds`() {
        val cat = CatDto(
            id = "xyz789",
            url = "https://example.com/another.jpg",
            breeds = null
        )

        val jsonString = json.encodeToString(CatDto.serializer(), cat)
        val parsedCat = json.decodeFromString<CatDto>(jsonString)

        assertEquals(cat.id, parsedCat.id)
        assertEquals(cat.url, parsedCat.url)
        assertNull(parsedCat.breeds)
    }

    @Test
    fun `test BreedDto with null optional fields`() {
        val breed = BreedDto(
            id = "no1",
            name = "Unknown",
            temperament = null,
            life_span = null,
            weight = null,
            height = null
        )

        val jsonString = json.encodeToString(BreedDto.serializer(), breed)
        val parsedBreed = json.decodeFromString<BreedDto>(jsonString)

        assertEquals(breed.id, parsedBreed.id)
        assertEquals(breed.name, parsedBreed.name)
        assertEquals(breed.life_span, parsedBreed.life_span)
    }

    @Test
    fun `test WeightDto and HeightDto serialization`() {
        val weight = WeightDto("10-15", "4.5-6.8")
        val height = HeightDto("12-14", "30-36")

        val weightJson = json.encodeToString(WeightDto.serializer(), weight)
        val heightJson = json.encodeToString(HeightDto.serializer(), height)

        val parsedWeight = json.decodeFromString<WeightDto>(weightJson)
        val parsedHeight = json.decodeFromString<HeightDto>(heightJson)

        assertEquals(weight.metric, parsedWeight.metric)
        assertEquals(weight.imperial, parsedWeight.imperial)
        assertEquals(height.metric, parsedHeight.metric)
        assertEquals(height.imperial, parsedHeight.imperial)
    }
}