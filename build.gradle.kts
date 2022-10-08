plugins {
    id(Plugins.ANDROID_APPLICATION).version(Versions.AGP).apply(false)
    id(Plugins.KOTLIN).version(Versions.KOTLIN).apply(false)
    id(Plugins.DETEKT).version(Versions.DETEKT).apply(false)
}

allprojects {
    apply(plugin = Plugins.DETEKT)

    tasks.withType(io.gitlab.arturbosch.detekt.Detekt::class) {
        basePath = projectDir.toString()
        debug = true
        parallel = true

        reports {
            md.required.set(false)
            sarif.required.set(false)
            txt.required.set(false)
            xml.required.set(false)
            html.required.set(true)
            html.outputLocation.set(file("build/reports/detekt.html"))
        }
    }

    tasks.whenTaskAdded { if (name == "preBuild") dependsOn("detekt") }
}
