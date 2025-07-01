package com.niolasdev.randommouse

import com.niolasdev.network.BreedDto
import com.niolasdev.network.CatDto
import com.niolasdev.randommouse.data.BreedDataMapper
import com.niolasdev.randommouse.data.CatDataMapper
import com.niolasdev.storage.model.BreedDbo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class CatDataMapperTest {

    private val breedMapper = mockk<BreedDataMapper>()
    private val catMapper = CatDataMapper(breedMapper)

    @Test
    fun `from should map CatDto to CatDbo using BreedDataMapper`() {
        val breedDto = BreedDto(
            id = "b1",
            name = "Siamese",
            temperament = "Vocal",
            life_span = "12-15",
            weight = null,
            height = null,
            description = "Very social",
            origin = "Thailand",
            country_code = "th"
        )

        val breedDbo = BreedDbo(
            id = "b1",
            name = "Siamese",
            temperament = "Vocal",
            lifeSpan = "12-15",
            description = "Very social",
            origin = "Thailand",
            countryCode = "th",
            weight = null,
            height = null
        )

        every { breedMapper.from(listOf(breedDto)) } returns listOf(breedDbo)

        val dto = CatDto(
            id = "cat1",
            url = "https://cat.com/siamese.jpg",
            breeds = listOf(breedDto)
        )

        val result = catMapper.from(dto)

        assertEquals("cat1", result.catId)
        assertEquals("https://cat.com/siamese.jpg", result.url)
        assertEquals(listOf(breedDbo), result.breeds)

        verify(exactly = 1) { breedMapper.from(dto.breeds) }
    }
}