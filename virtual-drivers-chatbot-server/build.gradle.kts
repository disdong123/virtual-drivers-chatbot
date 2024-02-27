dependencies {
    implementation(project(":virtual-drivers-chatbot-persistence"))
    implementation(project(":virtual-drivers-chatbot-api-client"))
    implementation(project(":virtual-drivers-chatbot-common"))
    implementation(libs.spring.boot.starter.web)
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
