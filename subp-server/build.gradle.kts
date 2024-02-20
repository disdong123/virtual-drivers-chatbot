dependencies {
    implementation(project(":subp-persistence"))
    implementation(project(":subp-common"))
    implementation(libs.spring.boot.starter.web)
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
