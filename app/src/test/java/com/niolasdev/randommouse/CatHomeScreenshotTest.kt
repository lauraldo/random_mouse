package com.niolasdev.randommouse

import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class CatHomeScreenshotTest(
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
    fun catHomeWithData() {
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
            )
        )

        captureComposeScreenshot(device = device) {
            CatHome(
                state = CatListState.Data(cats),
                onRefresh = {},
                onCatClick = {}
            )
        }
    }

    @Test
    fun catHomeLoading() {
        captureComposeScreenshot(device = device) {
            CatHome(
                state = CatListState.Loading,
                onRefresh = {},
                onCatClick = {}
            )
        }
    }

    @Test
    fun catHomeError() {
        captureComposeScreenshot(device = device) {
            CatHome(
                state = CatListState.Error("Network error"),
                onRefresh = {},
                onCatClick = {}
            )
        }
    }

    @Test
    fun catHomeEmpty() {
        captureComposeScreenshot(device = device) {
            CatHome(
                state = CatListState.Data(emptyList()),
                onRefresh = {},
                onCatClick = {}
            )
        }
    }
}
