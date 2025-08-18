package com.niolasdev.randommouse

import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers

enum class Device(val qualifier: String) {
    NEXUS_ONE(RobolectricDeviceQualifiers.NexusOne),
    NEXUS_9(RobolectricDeviceQualifiers.Nexus9),
    PIXEL_4(RobolectricDeviceQualifiers.Pixel4),
    PIXEL_4XL(RobolectricDeviceQualifiers.Pixel4XL),
    PIXEL_4A(RobolectricDeviceQualifiers.Pixel4a),
    PIXEL_5(RobolectricDeviceQualifiers.Pixel5),
    PIXEL_6(RobolectricDeviceQualifiers.Pixel6),
    PIXEL_6PRO(RobolectricDeviceQualifiers.Pixel6Pro),
    PIXEL_6A(RobolectricDeviceQualifiers.Pixel6a),
    PIXEL_7(RobolectricDeviceQualifiers.Pixel7),
    PIXEL_7PRO(RobolectricDeviceQualifiers.Pixel7Pro),
    TABLET(RobolectricDeviceQualifiers.Nexus9), // Using Nexus9 as tablet
    FIGMA_SCREEN("w360dp-h760dp-xxhdpi"), // aka device for compare actual with figma by ImageDiffer
}