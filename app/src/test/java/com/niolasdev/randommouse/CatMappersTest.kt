package com.niolasdev.randommouse

import com.niolasdev.network.BreedDto
import com.niolasdev.network.CatDto
import com.niolasdev.randommouse.data.BreedMapper
import com.niolasdev.randommouse.data.CatMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class CatMappersTest {

    val breedMapper = BreedMapper()
    val catMapper = CatMapper(breedMapper)

    @Test
    fun testMapping() {

        val breedDto = mockk<BreedDto>().apply {
            every { id } returns "020"
            every { name } returns "Canadian"
            every { temperament } returns null
            every { description } returns null
            every { origin } returns null
            every { country_code } returns null
        }

        val catDto = mockk<CatDto>().apply {
            every { id } returns "010"
            every { url } returns "localhost"
            every { breeds } returns listOf(breedDto)
        }

        val cat = catMapper.from(catDto)

        verify(exactly = 1) {
            breedMapper.from(listOf(breedDto))
        }

        assertEquals("010", cat.id)
        assertEquals("localhost", cat.url)
        assertEquals("020", cat.breeds?.get(0)?.id)
        assertEquals("Canadian", cat.breeds?.get(0)?.name)
    }


}