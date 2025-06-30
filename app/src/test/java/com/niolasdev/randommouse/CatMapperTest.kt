package com.niolasdev.randommouse

import com.niolasdev.network.BreedDto
import com.niolasdev.network.CatDto
import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.BreedMapper
import com.niolasdev.randommouse.data.CatMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CatMapperTest {

    private val breedMapper = mockk<BreedMapper>()
    private val catMapper = CatMapper(breedMapper)

    @Test
    fun `from should map CatDto to Cat with mapped breeds`() {
        val breedDtoList = listOf(
            BreedDto("b1", "Siamese", "Smart", "10-12", null, null, null, null, null)
        )

        val mappedBreeds = listOf(
            Breed("b1", "Siamese", "Smart", null, null, null)
        )

        every { breedMapper.from(breedDtoList) } returns mappedBreeds

        val catDto = CatDto("cat123", "https://cat.url", breedDtoList)
        val result = catMapper.from(catDto)

        assertEquals("cat123", result.id)
        assertEquals("https://cat.url", result.url)
        assertEquals(mappedBreeds, result.breeds)

        verify(exactly = 1) { breedMapper.from(breedDtoList) }
    }

    @Test
    fun `from should map CatDto to Cat with empty breed list when dto breeds is null`() {
        every { breedMapper.from(null) } returns emptyList()

        val catDto = CatDto("cat456", "https://cat2.url", null)
        val result = catMapper.from(catDto)

        assertEquals("cat456", result.id)
        assertEquals("https://cat2.url", result.url)
        assertTrue(result.breeds?.isEmpty() == true)

        verify(exactly = 1) { breedMapper.from(null) }
    }
}