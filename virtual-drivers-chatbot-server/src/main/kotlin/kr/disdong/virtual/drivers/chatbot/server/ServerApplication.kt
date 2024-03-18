package kr.disdong.virtual.drivers.chatbot.server

import kr.disdong.virtual.drivers.chatbot.api.client.ApiClientApplication
import kr.disdong.virtual.drivers.chatbot.server.chatbot.helper.DefaultChatPrinter
import kr.disdong.virtual.drivers.chatbot.server.chatbot.helper.DrivingDirectionChatBot
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import kotlin.system.exitProcess

@SpringBootApplication
@Import(ApiClientApplication::class)
class ServerApplication(
    private val drivingDirectionChatBot: DrivingDirectionChatBot,
    private val defaultChatPrinter: DefaultChatPrinter,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        defaultChatPrinter.print("(대화 종료를 원하신다면 eq 를 입력하세요.)")

        val context = drivingDirectionChatBot.chat()
        defaultChatPrinter.print(context)

        while (true) {
            val input = readlnOrNull() ?: ""
            if (input == "eq") {
                exitProcess(0)
            }

            drivingDirectionChatBot.chat(context, input)

            defaultChatPrinter.print(context)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
