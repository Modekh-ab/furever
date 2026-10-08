plugins { id("dev.kikugie.loom-back-compat") }

version = "${sc.current.version}-${property("mod_version")}-fabric"
base.archivesName = "furever"
group = "net.mondless"
val requiredJava = if (sc.current.parsed >= "26.1") 25 else 21

repositories { maven("https://maven.terraformersmc.com/releases/"); maven("https://maven.shedaniel.me/") }
dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    if (sc.current.parsed < "26.1") {
        mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    } else {
        loomx.applyMojangMappings()
    }
    if (sc.current.parsed >= "26.1") {
        implementation("net.fabricmc:fabric-loader:${rootProject.property("loader_version")}")
        implementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
        compileOnly("com.terraformersmc:modmenu:${property("modmenu_version")}")
        compileOnly("me.shedaniel.cloth:cloth-config-fabric:${property("cloth_config_version")}")
    } else {
        modImplementation("net.fabricmc:fabric-loader:${rootProject.property("loader_version")}")
        modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
        modCompileOnly("com.terraformersmc:modmenu:${property("modmenu_version")}")
        modCompileOnly("me.shedaniel.cloth:cloth-config-fabric:${property("cloth_config_version")}")
    }
}
java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.toVersion(requiredJava)
    targetCompatibility = JavaVersion.toVersion(requiredJava)
    toolchain.languageVersion = JavaLanguageVersion.of(requiredJava)
}
tasks.withType<JavaCompile>().configureEach { options.release = requiredJava }
tasks.processResources {
    val props = mapOf("version" to project.version, "minecraft_version" to sc.current.version, "java_version" to requiredJava)
    inputs.properties(props)
    filesMatching(listOf("fabric.mod.json", "*.mixins.json")) { expand(props) }
}
tasks.register<Copy>("collectJar") {
    group = "build"
    dependsOn(loomx.modJar)
    from(loomx.modJar.flatMap { it.archiveFile })
    into(rootProject.layout.projectDirectory.dir("dist"))
}
