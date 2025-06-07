package com.niolasdev.randommouse

import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CatModelsTest {

    @Test
    fun `Cat constructor sets fields correctly`() {
        val breed = Breed("abys", "Abyssinian", "Active, Energetic")
        val cat = Cat("cat123", "https://example.com/cat.jpg", listOf(breed))

        assertEquals("cat123", cat.id)
        assertEquals("https://example.com/cat.jpg", cat.url)
        assertNotNull(cat.breeds)
        assertEquals(1, cat.breeds?.size)
        assertEquals("abys", cat.breeds?.get(0)?.id)
    }

    @Test
    fun `Breed constructor sets fields correctly`() {
        val breed = Breed("sibe", "Siberian", "Affectionate, Loyal")

        assertEquals("sibe", breed.id)
        assertEquals("Siberian", breed.name)
        assertEquals("Affectionate, Loyal", breed.temperament)
    }

    @Test
    fun `Breed toString returns expected format`() {
        val breed = Breed("pers", "Persian", "Calm, Quiet")
        val expected = "pers; Persian; Calm, Quiet"

        assertEquals(expected, breed.toString())
    }

    @Test
    fun `Cat toString returns expected format with breeds`() {
        val breed1 = Breed("b1", "Bengal", "Wild, Smart")
        val breed2 = Breed("b2", "Maine Coon", "Gentle Giant")
        val cat = Cat("cat999", "https://cat.img", listOf(breed1, breed2))

        val actual = cat.toString()

        // Note: toString() on list uses each element’s toString()
        assertTrue(actual.startsWith("^_^ cat999; ["))
        assertTrue(actual.contains("b1; Bengal; Wild, Smart"))
        assertTrue(actual.contains("b2; Maine Coon; Gentle Giant"))
    }

    @Test
    fun `Cat toString works when breeds is null`() {
        val cat = Cat("cat000", "https://no-breed.jpg", null)
        val expected = "^_^ cat000; [null]"

        assertEquals(expected, cat.toString())
    }
}