package com.niolasdev.randommouse

import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.BreedDboMapper
import com.niolasdev.randommouse.data.CatDboMapper
import com.niolasdev.storage.model.BreedDbo
import com.niolasdev.storage.model.CatDbo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CatDboMapperTest {

    private val breedDboMapper = mockk<BreedDboMapper>()
    private val mapper = CatDboMapper(breedDboMapper)

    @Test
    fun `from should map CatWithBreeds to Cat using BreedDboMapper`() {
        val breedDbos = listOf(
            BreedDbo(
                id = "b1",
                name = "Siberian",
                temperament = "Loyal",
                lifeSpan = null,
                weight = null,
                height = null,
                origin = null,
                countryCode = null,
                description = null,
            )
        )
        val mappedBreeds = listOf(
            Breed(
                "b1",
                "Siberian",
                "Loyal",
                description = null,
                origin = null,
                countryFlagUrl = null
            )
        )

        every { breedDboMapper.from(breedDbos) } returns mappedBreeds

        val catWithBreeds = CatDbo(
            catId = "c1", url = "https://cat.com/image.jpg",
            breeds = breedDbos
        )

        val result = mapper.from(catWithBreeds)

        assertEquals("c1", result.id)
        assertEquals("https://cat.com/image.jpg", result.url)
        assertEquals(mappedBreeds, result.breeds)

        verify(exactly = 1) { breedDboMapper.from(breedDbos) }
    }

    @Test
    fun `from should handle null breeds`() {
        every { breedDboMapper.from(null) } returns emptyList()

        val catWithBreeds = CatDbo(
            catId = "c2", url = "https://example.com/img2.jpg", breeds = null
        )

        val result = mapper.from(catWithBreeds)

        assertEquals("c2", result.id)
        assertEquals("https://example.com/img2.jpg", result.url)
        assertTrue(result.breeds?.isEmpty() == true)

        verify(exactly = 1) { breedDboMapper.from(null) }
    }
}