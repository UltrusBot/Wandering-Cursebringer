import me.ultrusmods.wanderingcursebringer.gradle.Properties
import me.modmuss50.mpp.PublishModTask

plugins {
    id("conventions.common")
    id("net.neoforged.moddev")
    id("me.modmuss50.mod-publish-plugin")
}

sourceSets {
    create("generated") {
        resources {
            srcDir("src/generated/resources")
        }
    }
}
neoForge {
    neoFormVersion = Properties.NEOFORM_VERSION
    parchment {
        minecraftVersion = "1.21" // TODO: Put this back to minecraft version once parchment is 1.21.1
        mappingsVersion = Properties.PARCHMENT_VERSION
    }
    addModdingDependenciesTo(sourceSets["test"])

    val at = file("src/main/resources/${Properties.MOD_ID}.cfg")
    if (at.exists())
        setAccessTransformers(at)
    validateAccessTransformers = true
}

dependencies {
    compileOnly(libs.mixin.extras)
    annotationProcessor(libs.mixin.extras)
    compileOnly(libs.fabric.mixin)
}

configurations {
    register("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonTestResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets["main"].java.sourceDirectories.singleFile)
    add("commonResources", sourceSets["main"].resources.sourceDirectories.singleFile)
    add("commonResources", sourceSets["generated"].resources.sourceDirectories.singleFile)
    add("commonTestResources", sourceSets["test"].resources.sourceDirectories.singleFile)
}