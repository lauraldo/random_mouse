# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ===== KOTLINX SERIALIZATION =====
# Keep serializable classes
-keepclassmembers class com.niolasdev.randommouse.data.** {
    *** Companion;
}
-keepclasseswithmembers class com.niolasdev.randommouse.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# ===== HILT =====
# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }

# ===== COMPOSE =====
# Keep Compose related classes
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** {
    *;
}

# ===== VIEWMODEL =====
# Keep ViewModel classes
-keep class com.niolasdev.randommouse.CatsViewModel { *; }
-keep class com.niolasdev.randommouse.CatListState { *; }
-keep class com.niolasdev.randommouse.ui.CatDetailViewModel { *; }
-keep class com.niolasdev.randommouse.ui.CatDetailState { *; }

# ===== NETWORK =====
# Keep network related classes (assuming you have network module)
-keep class com.niolasdev.randommouse.network.** { *; }

# ===== STORAGE =====
# Keep storage related classes (assuming you have storage module)
-keep class com.niolasdev.randommouse.storage.** { *; }

# ===== LOGGING =====
# Keep logging for debugging
-keepclassmembers class * {
    @android.util.Log *;
}

# ===== COROUTINES =====
# Keep coroutines related classes
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep enum values
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ===== RETROFIT =====
# Keep Retrofit related classes and interfaces
-keepattributes Signature
-keepattributes *Annotation*

# Keep Retrofit interfaces
-keepclasseswithmembers interface * {
    @retrofit2.http.* <methods>;
}

# Keep Retrofit annotations
-keep @retrofit2.http.* interface * { *; }

# Keep Retrofit call adapters
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Keep OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Keep Kotlin serialization for Retrofit
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep your API interfaces
-keep interface com.niolasdev.randommouse.network.** { *; }
-keep interface com.niolasdev.randommouse.data.** { *; }

# Keep generic types for Retrofit
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep all Retrofit interfaces and their methods
-keep interface * {
    @retrofit2.http.* <methods>;
}

# Keep Retrofit service interfaces
-keep,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions


# ===== KOTLIN RESULT =====
# Keep Kotlin Result type
-keep class kotlin.Result { *; }
-keep class kotlin.Result$Companion { *; }
-keepclassmembers class kotlin.Result {
    *;
}
