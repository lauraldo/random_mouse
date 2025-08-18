package com.niolasdev.randommouse

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.dropbox.differ.SimpleImageComparator
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.util.Locale

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    application = Application::class,
    sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE]
)
abstract class BaseScreenshotTest(
    isHardwareRenderEnabled: Boolean = true
) {
    // Default values for testing
    protected val locale: Locale = Locale.ENGLISH

    init {
        switchHardwareRenderMode(isHardwareRenderEnabled)
    }

    @OptIn(ExperimentalRoborazziApi::class)
    private val roborazziOptions = RoborazziOptions(
        compareOptions = RoborazziOptions.CompareOptions(
            imageComparator = SimpleImageComparator(
                maxDistance = IMAGE_COMPARATOR_MAX_DISTANCE_DEFAULT,
                hShift = IMAGE_COMPARATOR_H_SHIFT,
            ),
            changeThreshold = ALLOWED_CHANGE_THRESHOLD,
        )
    )

    @get:Rule
    val roborazziRule = RoborazziRule(
        options = RoborazziRule.Options(
            outputDirectoryPath = REFERENCE_IMAGES_PATH,
            outputFileProvider = (FileProvider::get),
            roborazziOptions = roborazziOptions
        )
    )

    protected val context: Context
        get() = RuntimeEnvironment.getApplication()

    protected val resources: Resources
        get() = context.resources

    protected fun captureComposeScreenshot(
        device: Device = Device.PIXEL_5,
        content: @androidx.compose.runtime.Composable () -> Unit
    ) {
        RuntimeEnvironment.setQualifiers("${locale.qualifier}-${device.qualifier}")

        val activity = org.robolectric.Robolectric.buildActivity(ComponentActivity::class.java)
            .create()
            .start()
            .resume()
            .visible()
            .get()

        val composeView = ComposeView(activity).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setContent {
                content()
            }
        }

        activity.setContentView(composeView)

        val width = 1080
        val height = 1920
        composeView.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
        composeView.layout(0, 0, width, height)

        org.robolectric.shadows.ShadowLooper.shadowMainLooper().idle()

        composeView.captureRoboImage()
    }

    protected fun View.setViewSize(width: Int, height: Int) {
        layoutParams = LinearLayout.LayoutParams(width, height)
    }

    protected fun View.setViewSizeFrameLayout(width: Int, height: Int) {
        layoutParams = FrameLayout.LayoutParams(width, height)
    }

    protected fun View.setSameViewSize(size: Int) {
        setViewSize(size, size)
    }

    /*
    * Return empty View instead Dialog for compatible with main test function
    */
    protected fun Dialog.asStubView(): View {
        if (!isShowing) show()
        return FrameLayout(context)
    }

    /*
    * Return Dialog ViewInteraction for compatible with main test function
    */
    @Suppress("UnusedReceiverParameter")
    protected fun View.getDialogViewInteraction(): ViewInteraction {
        return onView(isRoot()).inRoot(isDialog())
    }

    private fun switchHardwareRenderMode(isHardwareRenderEnabled: Boolean) {
        val currentRenderMode = if (isHardwareRenderEnabled) HARDWARE_MODE else SOFTWARE_MODE
        System.setProperty(PIXEL_COPY_RENDERER_MODE, currentRenderMode)
    }
}

/*
* Extension property for Locale to get qualifier
*/
val Locale.qualifier: String
    get() = when (this) {
        Locale.ENGLISH -> "en"
        Locale("ru") -> "ru"
        else -> "en"
    }

private const val ALLOWED_CHANGE_THRESHOLD = 0.05f

private const val IMAGE_COMPARATOR_MAX_DISTANCE_DEFAULT = 0.007f
private const val IMAGE_COMPARATOR_H_SHIFT = 1

private const val REFERENCE_IMAGES_PATH = "src/test/assets/test_refs"
private const val PIXEL_COPY_RENDERER_MODE = "robolectric.pixelCopyRenderMode"
private const val HARDWARE_MODE = "hardware"
private const val SOFTWARE_MODE = "software"