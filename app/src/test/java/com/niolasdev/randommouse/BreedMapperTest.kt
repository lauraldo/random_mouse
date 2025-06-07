package com.niolasdev.randommouse

import com.niolasdev.network.BreedDto
import com.niolasdev.randommouse.data.BreedMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BreedMapperTest {

    private val mapper = BreedMapper()

    @Test
    fun `from should map list of BreedDto to list of Breed`() {
        val dtos = listOf(
            BreedDto("id1", "BreedOne", "Friendly", "12-15", null, null),
            BreedDto("id2", "BreedTwo", "Calm", "10-12", null, null)
        )

        val result = mapper.from(dtos)

        assertEquals(2, result.size)
        assertEquals("id1", result[0].id)
        assertEquals("BreedOne", result[0].name)
        assertEquals("Friendly", result[0].temperament)
    }

    @Test
    fun `from should return empty list when input is null`() {
        val result = mapper.from(null)

        assertTrue(result.isEmpty())
    }
}