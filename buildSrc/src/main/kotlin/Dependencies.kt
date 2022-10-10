/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

object Versions {
    const val KOTLIN = "1.7.10"
    const val AGP = "7.2.0"
    const val CORE_KTX = "1.8.0"
    const val APPCOMPAT = "1.5.1"
    const val MATERIAL = "1.6.1"
    const val HILT = "2.44"
    const val NAVIGATION = "2.5.2"
    const val ROOM = "2.4.3"
    const val FIREBASE_BOM = "30.4.1"
    const val DETEKT = "1.22.0-RC1"
    const val GOOGLE_SERVICES = "4.3.13"
    const val RETROFIT = "2.9.0"
    const val MOSHI = "1.14.0"
    const val COROUTINES = "1.6.4"
    const val JUNIT = "4.13.2"
    const val EXT_JUNIT = "1.1.3"
    const val ESPRESSO_CORE = "3.4.0"
}

object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KOTLIN = "org.jetbrains.kotlin.android"
    const val KAPT = "kotlin-kapt"
    const val HILT = "com.google.dagger.hilt.android"
    const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
    const val GOOGLE_SERVICES = "com.google.gms.google-services"
    const val DETEKT = "io.gitlab.arturbosch.detekt"
}

object ProcessingTools {
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.HILT}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.ROOM}" }
    val moshiKotlinCodegen by lazy { "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}" }
}

object Libs {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.CORE_KTX}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.APPCOMPAT}" }
    val material by lazy { "com.google.android.material:material:${Versions.MATERIAL}" }
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.HILT}" }
    val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.ROOM}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.ROOM}" }
    val firebaseAnalyticsKtx by lazy { "com.google.firebase:firebase-analytics-ktx" }
    val firebaseStorageKtx by lazy { "com.google.firebase:firebase-storage-ktx" }
    val firebaseFirestoreKtx by lazy { "com.google.firebase:firebase-firestore-ktx" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}" }
    val moshiKotlin by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}" }
    val coroutinesPlayServices by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.COROUTINES}" }

    object Platform {
        val firebaseBom by lazy { "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}" }
    }
}

object TestLibs {
    val junit by lazy { "junit:junit:${Versions.JUNIT}" }
    val extJunit by lazy { "androidx.test.ext:junit:${Versions.EXT_JUNIT}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}" }
}
