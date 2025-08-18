package com.niolasdev.randommouse

import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.ui.widget.CatItem
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class CatItemScreenshotTest(
    private val device: Device,
) : BaseScreenshotTest() {

    private companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(
            name = "Device={0}"
        )
        fun data(): List<Any?> {
            return listOf(
                Device.PIXEL_5,
                Device.PIXEL_7,
                Device.TABLET
            )
        }
    }

    @Test
    fun catItemBasic() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = listOf(
                Breed(
                    id = "breed1",
                    name = "British Shorthair",
                    temperament = "Calm, affectionate",
                    description = "A calm and affectionate breed.",
                    origin = "UK",
                    countryFlagUrl = "https://example.com/flag.png"
                )
            )
        )

        captureComposeScreenshot(device = device) {
            CatItem(cat = cat)
        }
    }

    @Test
    fun catItemWithMultipleBreeds() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = listOf(
                Breed("breed1", "British Shorthair", "Calm", "Desc", "UK", ""),
                Breed("breed2", "Persian", "Gentle", "Desc", "IR", ""),
                Breed("breed3", "Maine Coon", "Friendly", "Desc", "US", "")
            )
        )

        captureComposeScreenshot(device = device) {
            CatItem(cat = cat)
        }
    }

    @Test
    fun catItemWithoutBreeds() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = null
        )

        captureComposeScreenshot(device = device) {
            CatItem(cat = cat)
        }
    }

    @Test
    fun catItemWithLongText() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = listOf(
                Breed(
                    id = "breed1",
                    name = "Very Long Breed Name That Should Be Truncated",
                    temperament = "Very long temperament description that should be truncated to fit in the available space",
                    description = "Very long description that should be truncated to fit in the available space. This is a very detailed description of the breed that includes information about its history, characteristics, and behavior. It should be long enough to test how the component handles text overflow.",
                    origin = "United Kingdom of Great Britain and Northern Ireland",
                    countryFlagUrl = "https://example.com/flag.png"
                )
            )
        )

        captureComposeScreenshot(device = device) {
            CatItem(cat = cat)
        }
    }
} 