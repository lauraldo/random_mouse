package com.niolasdev.randommouse

import com.niolasdev.randommouse.ui.widget.CatEmptyState
import com.niolasdev.randommouse.ui.widget.CatErrorState
import com.niolasdev.randommouse.ui.widget.CatLoadingAnimation
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class CatStatesScreenshotTest(
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
    fun catLoadingAnimation() {
        captureComposeScreenshot(device = device) {
            CatLoadingAnimation()
        }
    }

    @Test
    fun catEmptyState() {
        captureComposeScreenshot(device = device) {
            CatEmptyState(onRefresh = {})
        }
    }

    @Test
    fun catErrorState() {
        captureComposeScreenshot(device = device) {
            CatErrorState(message = "Something went wrong", onRetry = {})
        }
    }

    @Test
    fun catEmptyStateWithCustomMessage() {
        captureComposeScreenshot(device = device) {
            CatEmptyState(onRefresh = {})
        }
    }

    @Test
    fun catErrorStateWithCustomMessage() {
        captureComposeScreenshot(device = device) {
            CatErrorState(message = "Failed to load cats. Please try again.", onRetry = {})
        }
    }

    @Test
    fun catStatesComparison() {
        captureComposeScreenshot(device = device) {
            androidx.compose.foundation.layout.Column {
                CatLoadingAnimation()
                CatEmptyState(onRefresh = {})
                CatErrorState(message = "Something went wrong", onRetry = {})
            }
        }
    }
} 