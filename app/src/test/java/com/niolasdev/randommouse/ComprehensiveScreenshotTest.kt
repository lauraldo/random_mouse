package com.niolasdev.randommouse

import androidx.compose.foundation.layout.Column
import com.github.takahirom.roborazzi.captureRoboImage
import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.CatListState
import com.niolasdev.randommouse.ui.CatDetailState
import com.niolasdev.randommouse.ui.widget.CatDetailCard
import com.niolasdev.randommouse.ui.widget.CatEmptyState
import com.niolasdev.randommouse.ui.widget.CatErrorState
import com.niolasdev.randommouse.ui.widget.CatItem
import com.niolasdev.randommouse.ui.widget.CatList
import com.niolasdev.randommouse.ui.widget.CatLoadingAnimation
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class ComprehensiveScreenshotTest(
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
    fun basicComponents() {
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
            Column {
                CatItem(cat = cat)
                CatDetailCard(state = CatDetailState.Data(cat))
                CatLoadingAnimation()
                CatEmptyState(onRefresh = {})
                CatErrorState(message = "Something went wrong", onRetry = {})
            }
        }
    }

    @Test
    fun multipleBreedsScenario() {
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
            Column {
                CatItem(cat = cat)
                CatDetailCard(state = CatDetailState.Data(cat))
            }
        }
    }

    @Test
    fun allStatesScenario() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = listOf(
                Breed("breed1", "British Shorthair", "Calm", "Desc", "UK", "")
            )
        )

        captureComposeScreenshot(device = device) {
            Column {
                CatLoadingAnimation()
                CatEmptyState(onRefresh = {})
                CatErrorState(message = "Something went wrong", onRetry = {})
                CatItem(cat = cat)
                CatDetailCard(state = CatDetailState.Data(cat))
                CatDetailCard(state = CatDetailState.Loading)
                CatDetailCard(state = CatDetailState.Error("Network error"))
            }
        }
    }

    @Test
    fun customMessagesScenario() {
        captureComposeScreenshot(device = device) {
            Column {
                CatEmptyState(onRefresh = {})
                CatErrorState(message = "Failed to load cats. Please try again.", onRetry = {})
            }
        }
    }

    @Test
    fun longTextScenario() {
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
            Column {
                CatItem(cat = cat)
                CatDetailCard(state = CatDetailState.Data(cat))
            }
        }
    }

    @Test
    fun shortTextScenario() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = listOf(
                Breed(
                    id = "breed1",
                    name = "Cat",
                    temperament = "Calm",
                    description = "A cat.",
                    origin = "UK",
                    countryFlagUrl = "https://example.com/flag.png"
                )
            )
        )

        captureComposeScreenshot(device = device) {
            Column {
                CatItem(cat = cat)
                CatDetailCard(state = CatDetailState.Data(cat))
            }
        }
    }

    @Test
    fun noBreedsScenario() {
        val cat = Cat(
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            breeds = null
        )

        captureComposeScreenshot(device = device) {
            Column {
                CatItem(cat = cat)
                CatDetailCard(state = CatDetailState.Data(cat))
            }
        }
    }

    @Test
    fun manyItemsScenario() {
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
    fun emptyStatesScenario() {
        captureComposeScreenshot(device = device) {
            Column {
                CatList(
                    cats = emptyList(),
                    isLoading = false,
                    onRefresh = {},
                    onCatClick = {}
                )
                CatHome(
                    state = CatListState.Data(emptyList()),
                    onRefresh = {},
                    onCatClick = {}
                )
            }
        }
    }

    @Test
    fun complexLayoutScenario() {
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
                breeds = null
            )
        )

        captureComposeScreenshot(device = device) {
            Column {
                CatHome(
                    state = CatListState.Data(cats),
                    onRefresh = {},
                    onCatClick = {}
                )
                CatDetailCard(state = CatDetailState.Data(cats.first()))
                CatLoadingAnimation()
                CatEmptyState(onRefresh = {})
                CatErrorState(message = "Something went wrong", onRetry = {})
            }
        }
    }
} 