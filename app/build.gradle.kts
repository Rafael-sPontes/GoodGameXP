plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt") //plugin kapt (anotações)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.rafael.appdev.goodgamexp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.rafael.appdev.goodgamexp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Material Design Components
    implementation("com.google.android.material:material:1.13.0")

    //ROOM (PERSISTÊNCIA DE DADOS)
    val roomVersion = "2.8.3" //Versão atual v2.8.3
    /*val roomVersion = "2.6.1" //Versão atual v2.6.1*/

    //1. Dependência principal do Room e suporte a Coroutines/Flow
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //2. Processador de anotações: Usar KSP (Kotlin Symbol Processing)
    //Ele gera o código necessário do Room em tempo de compilação.
    /*ksp("androidx.room:room-compiler:$roomVersion")*/

    //Coroutines (Necessário para usar Flow e suspend functions no DAO)
    val coroutinesVersion = "1.10.2" // Versão atual v1.10.2
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    //Hilt - Injeção de Dependência
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //Lifecycle & ViewModel (Necessário para o GameViewModel)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2") //Facilita chamar o viewmodel na activity
}