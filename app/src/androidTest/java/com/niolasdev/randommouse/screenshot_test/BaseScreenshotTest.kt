package com.niolasdev.randommouse.screenshot_test

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onRoot
import com.dropbox.dropshots.Dropshots
import com.dropbox.dropshots.ThresholdValidator
import com.niolasdev.network.BuildConfig
import org.junit.Rule
import java.io.File

open class BaseScreenshotTest {
    @get:Rule
    val dropshots = Dropshots(
        resultValidator = ThresholdValidator(0.15f)
    )

    // Helper method to capture screenshots
    fun captureScreenshot(composeRule: ComposeContentTestRule, name: String) {
        val bitmap: Bitmap = composeRule.onRoot().captureToImage().asAndroidBitmap()

        // Create screenshots directory if it doesn't exist
        val screenshotsDir = File("build/screenshots")
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs()
        }

        // Save screenshot to the specified directory
        dropshots.assertSnapshot(bitmap, name, "build/screenshots")
    }
}