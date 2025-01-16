buildscript {
    val kotlinVersion = "1.9.0"
    
    repositories {
        google()
        mavenCentral()
    }
    
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
