dependencies {
    implementation(project(":virtual-drivers-gpt-persistence"))
    implementation(project(":virtual-drivers-gpt-api-client"))
    implementation(project(":virtual-drivers-gpt-common"))
    implementation(libs.spring.boot.starter.web)
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
