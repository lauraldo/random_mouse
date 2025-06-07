package com.niolasdev.randommouse

import com.niolasdev.randommouse.data.BreedDboMapper
import com.niolasdev.storage.model.BreedDbo
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class BreedDboMapperTest {

    private val mapper = BreedDboMapper()

    @Test
    fun `from should map list of BreedDbo to list of Breed`() {
        val dbos = listOf(
            BreedDbo(
                breedId = "id1",
                name = "Persian",
                temperament = "Calm",
                lifespan = null,
                imperialWeight = null,
                metricWeight = null,
                imperialHeight = null,
                metricHeight = null,
            ),
            BreedDbo(
                breedId = "id2",
                name = "Bengal",
                temperament = "Active",
                lifespan = null,
                imperialWeight = null,
                metricWeight = null,
                imperialHeight = null,
                metricHeight = null,
            ),
        )

        val result = mapper.from(dbos)

        assertEquals(2, result.size)
        assertEquals("id1", result[0].id)
        assertEquals("Persian", result[0].name)
        assertEquals("Calm", result[0].temperament)
    }

    @Test
    fun `from should return empty list when input is null`() {
        val result = mapper.from(null)

        assertTrue(result.isEmpty())
    }
}