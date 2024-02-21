package kr.disdong.springboot.template.server

import kr.disdong.springboot.template.jpa.PersistenceApplication
import kr.disdong.virtual.drivers.gpt.api.client.ApiClientApplication
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client.ChatBotClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(PersistenceApplication::class, ApiClientApplication::class)
class ServerApplication(
    private val chatBotClient: ChatBotClient,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        println("Hello, world!")
        chatBotClient.call()
    }
}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
