package kr.disdong.springboot.template.server

import kr.disdong.springboot.template.jpa.PersistenceApplication
import kr.disdong.virtual.drivers.gpt.api.client.ApiClientApplication
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client.ChatBotClient
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.DrivingDirectionApiRequest
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.VirtualDriversClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(PersistenceApplication::class, ApiClientApplication::class)
class ServerApplication(
    private val chatBotClient: ChatBotClient,
    private val virtualDriversClient: VirtualDriversClient,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        println("Hello, world!")
        println(
            virtualDriversClient.createDrivingDirection(
                DrivingDirectionApiRequest(
                    "서울특별시 강남구 강남대로 396",
                    37.001,
                    127.001,
                    "서울특별시 강남구 역삼로 433",
                    37.002,
                    127.002,
                )
            )
        )
        chatBotClient.call()
    }
}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
