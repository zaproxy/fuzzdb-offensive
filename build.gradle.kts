import org.zaproxy.gradle.addon.AddOnStatus
import org.zaproxy.gradle.addon.manifest.tasks.ConvertChangelogToChanges

plugins {
    `java-library`
    eclipse
    id("org.zaproxy.add-on") version "0.1.0"
}

eclipse {
    classpath {
        // Prevent compilation of zapHomeFiles.
        sourceSets = listOf()
    }
}

version = "1"
description = "FuzzDB web backdoors which can be used with the ZAP fuzzer"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val generateManifestChanges by tasks.registering(ConvertChangelogToChanges::class) {
    changelog.set(file("CHANGELOG.md"))
    manifestChanges.set(file("$buildDir/zapAddOn/manifest-changes.html"))
}

zapAddOn {
    addOnName.set("FuzzDB Web Backdoors")
    addOnStatus.set(AddOnStatus.RELEASE)
    zapVersion.set("2.5.0")

    manifest {
        author.set("ZAP Dev Team")
        url.set("https://github.com/fuzzdb-project/fuzzdb/")
        changesFile.set(generateManifestChanges.flatMap { it.manifestChanges })
    }

    zapVersions {
        downloadUrl.set("https://github.com/zaproxy/fuzzdb-web-backdoors/releases/download/v$version")
    }
}