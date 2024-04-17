plugins {
    id("com.android.application")
}

android {
    namespace = "kk.projects.interviewtask"
    compileSdk = 34

    defaultConfig {
        applicationId = "kk.projects.interviewtask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    /* -----------------------retrofit, gson-------------------------------------------------------*/
    implementation("com.google.code.gson:gson:2.8.2")
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.okhttp:okhttp:2.5.0")
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0")

    implementation ("com.squareup.okhttp3:okhttp-urlconnection:4.2.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.17")


    implementation ("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
}