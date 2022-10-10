/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN)
    id(Plugins.KAPT)
    id(Plugins.HILT)
    id(Plugins.SAFE_ARGS)
    id(Plugins.GOOGLE_SERVICES)
}

android {
    namespace = Configs.APPLICATION_ID
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        applicationId = Configs.APPLICATION_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = Configs.VERSION_CODE
        versionName = Configs.VERSION_NAME

        testInstrumentationRunner = Configs.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Configs.JVM_TARGET
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Libs.coreKtx)
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.coroutinesPlayServices)
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")

    kapt(ProcessingTools.hiltCompiler)
    implementation(Libs.hiltAndroid)

    kapt(ProcessingTools.roomCompiler)
    implementation(Libs.roomRuntime)
    implementation(Libs.roomKtx)

    implementation(platform(Libs.Platform.firebaseBom))
    implementation(Libs.firebaseAnalyticsKtx)
    implementation(Libs.firebaseStorageKtx)
    implementation(Libs.firebaseFirestoreKtx)

    implementation(Libs.navigationUiKtx)
    implementation(Libs.navigationFragmentKtx)

    kapt(ProcessingTools.moshiKotlinCodegen)
    implementation(Libs.moshiKotlin)
    implementation(Libs.retrofit)

    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.extJunit)
    androidTestImplementation(TestLibs.espressoCore)
}
