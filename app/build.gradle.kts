import com.android.build.api.dsl.ApkSigningConfig
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "ru.rustore.rustorereviewjavaexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.rustore.rustorereviewjavaexample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        signingConfigs {
            // Замените на свою подпись!
            val debugStoreFile = rootProject.file("cert/release.keystore")
            val debugPropsFile = rootProject.file("cert/release.properties")
            val debugProps = Properties()
            debugPropsFile.inputStream().use(debugProps::load)
            val debugSigningConfig = getByName<ApkSigningConfig>("debug") {
                storeFile = debugStoreFile
                keyAlias = debugProps.getProperty("key_alias")
                keyPassword = debugProps.getProperty("key_password")
                storePassword = debugProps.getProperty("store_password")
            }
            signingConfig = debugSigningConfig
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName<ApkSigningConfig>("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //RuStore Review implementation
    implementation(libs.rustore.review)
}
