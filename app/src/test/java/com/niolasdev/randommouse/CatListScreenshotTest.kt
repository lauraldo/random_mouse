package com.niolasdev.randommouse

import com.github.takahirom.roborazzi.captureRoboImage
import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.ui.widget.CatList
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class CatListScreenshotTest(
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
    fun catListWithItems() {
        val cats = listOf(
            Cat(
                id = "cat1",
                url = "https://example.com/cat1.jpg",
                breeds = listOf(
                    Breed("breed1", "British Shorthair", "Calm", "Desc", "UK", "")
                )
            ),
            Cat(
                id = "cat2",
                url = "https://example.com/cat2.jpg",
                breeds = listOf(
                    Breed("breed2", "Persian", "Gentle", "Desc", "IR", "")
                )
            ),
            Cat(
                id = "cat3",
                url = "https://example.com/cat3.jpg",
                breeds = listOf(
                    Breed("breed3", "Maine Coon", "Friendly", "Desc", "US", "")
                )
            )
        )

        captureComposeScreenshot(device = device) {
            CatList(
                cats = cats,
                isLoading = false,
                onRefresh = {},
                onCatClick = {}
            )
        }
    }

    @Test
    fun catListEmpty() {
        captureComposeScreenshot(device = device) {
            CatList(
                cats = emptyList(),
                isLoading = false,
                onRefresh = {},
                onCatClick = {}
            )
        }
    }

    @Test
    fun catListWithManyItems() {
        val cats = (1..10).map { index ->
            Cat(
                id = "cat$index",
                url = "https://example.com/cat$index.jpg",
                breeds = listOf(
                    Breed("breed$index", "Breed $index", "Temperament $index", "Desc $index", "Country $index", "")
                )
            )
        }

        captureComposeScreenshot(device = device) {
            CatList(
                cats = cats,
                isLoading = false,
                onRefresh = {},
                onCatClick = {}
            )
        }
    }

    @Test
    fun catListWithSingleItem() {
        val cats = listOf(
            Cat(
                id = "cat1",
                url = "https://example.com/cat1.jpg",
                breeds = listOf(
                    Breed("breed1", "British Shorthair", "Calm", "Desc", "UK", "")
                )
            )
        )

        captureComposeScreenshot(device = device) {
            CatList(
                cats = cats,
                isLoading = false,
                onRefresh = {},
                onCatClick = {}
            )
        }
    }
} 