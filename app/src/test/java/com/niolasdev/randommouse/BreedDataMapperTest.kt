package com.niolasdev.randommouse

import com.niolasdev.network.BreedDto
import com.niolasdev.network.HeightDto
import com.niolasdev.network.WeightDto
import com.niolasdev.randommouse.data.BreedDataMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BreedDataMapperTest {

    private val mapper = BreedDataMapper()

    @Test
    fun `from should map list of BreedDto to list of BreedDbo`() {
        val dtos = listOf(
            BreedDto(
                id = "b1",
                name = "Abyssinian",
                temperament = "Active",
                life_span = "14-15",
                weight = WeightDto("7-10", "3-4.5"),
                height = HeightDto("9-11", "23-28"),
                description = "Elegant and playful",
                origin = "Egypt",
                country_code = "EG"
            )
        )

        val result = mapper.from(dtos)

        assertEquals(1, result.size)
        val breed = result[0]
        assertEquals("b1", breed.id)
        assertEquals("Abyssinian", breed.name)
        assertEquals("Active", breed.temperament)
        assertEquals("14-15", breed.lifeSpan)
        assertEquals("Elegant and playful", breed.description)
        assertEquals("Egypt", breed.origin)
        assertEquals("eg", breed.countryCode)
        assertEquals("7-10", breed.weight?.imperial)
        assertEquals("3-4.5", breed.weight?.metric)
        assertEquals("9-11", breed.height?.imperial)
        assertEquals("23-28", breed.height?.metric)
    }

    @Test
    fun `from should handle null input`() {
        val result = mapper.from(null)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `from should handle null nested fields`() {
        val dtos = listOf(
            BreedDto(
                id = "b2",
                name = "Unknown",
                temperament = null,
                life_span = null,
                weight = null,
                height = null,
                description = null,
                origin = null,
                country_code = null
            )
        )

        val result = mapper.from(dtos)

        assertEquals(1, result.size)
        val breed = result[0]
        assertEquals("b2", breed.id)
        assertNull(breed.weight?.metric)
        assertNull(breed.height?.metric)
        assertNull(breed.weight?.imperial)
        assertNull(breed.height?.imperial)
    }
}
