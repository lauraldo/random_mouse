package com.niolasdev.randommouse.screenshot_test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class SimpleScreenshotTest : BaseScreenshotTest() {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun simpleTest() {
        composeRule.setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Test Screenshot",
                    color = Color.Black
                )
            }
        }

        // Wait a bit for the content to be rendered
        Thread.sleep(1000)

        captureScreenshot(composeRule, "simple_test")
    }

    @Test
    fun coloredBoxTest() {
        composeRule.setContent {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Blue Box",
                    color = Color.White
                )
            }
        }

        Thread.sleep(1000)

        captureScreenshot(composeRule, "colored_box_test")
    }
}