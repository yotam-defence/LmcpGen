plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// Non-standard source layout: all Java source and resources live under src/
sourceSets {
    main {
        java {
            // Java source root is the top-level src/ directory
            setSrcDirs(listOf("src"))
            // Include only Java source files for compilation
            include("**/*.java")
        }
        resources {
            // Resources also rooted at src/ so classpath paths match getResource() calls
            setSrcDirs(listOf("src"))
            // Exclude Java source files from the resource copy
            exclude("**/*.java")
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "avtas.lmcp.lmcpgen.LmcpGenGUI"
        )
    }

    // Create a fat JAR (includes all runtime dependencies for future-proofing;
    // currently there are zero external dependencies)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
