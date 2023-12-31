plugins {
    id("com.android.application")
}

android {
    namespace = "com.braxly.lapancaproject"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.braxly.lapancaproject"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation ("com.android.volley:volley:1.2.1")

    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("com.google.android.material:material:1.8.0") //Boostrap de android - soolo diseños
    implementation ("com.airbnb.android:lottie:3.4.0") /*ANIMACIONES*/
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.code.gson:gson:2.9.1") /* FORMATO JSON */
    implementation ("com.github.bumptech.glide:glide:4.12.0")/* MEJORA LAS IMAGENES*/
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation ("com.sun.mail:android-mail:1.5.5")// PERMITE ENVIAR CORREO
    implementation ("com.sun.mail:android-activation:1.5.5")

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-android:3.12.4")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}