package kr.disdong.virtual.drivers.chatbot.server

import kr.disdong.virtual.drivers.chatbot.api.client.ApiClientApplication
import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.client.OpenAiChatBotClient
import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.dto.ChatContext
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.helper.DrivingDirectionScenario
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import kotlin.system.exitProcess

@SpringBootApplication
@Import(ApiClientApplication::class)
class ServerApplication(
    private val openAiChatBotClient: OpenAiChatBotClient,
    private val scenario: DrivingDirectionScenario
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        println("(대화 종료를 원하신다면 eq 를 입력하세요.)")
        val context = ChatContext.of()

        context.addSystemMessage(
            """
            너는 지금부터 대한민국의 지리에 대해선 누구보다 잘아는 내비게이션 챗봇이야.
            유저는 자신이 찾고 싶은 경로의 출발지와 도착지 주소를 입력할거야. 
            너는 그 주소의 위치(위도/경도 좌표)를 찾아내고, 이를 이용해서 경로를 생성해.
            경로를 생성하면 유저에게 경로에 대한 정보를 알려주고 추가로 궁금한게 있는지 물어봐. 
            유저가 궁금한게 없다면 대화를 종료해.
            """.trimIndent()
        )

        context.addSystemMessage(
            """
            앞으로 유저가 사용할건데, 정말로 대화하듯이 답해줘. 
            이제 유저에게 인사하고 길안내를 위한 출발지, 도착지 주소를 정중하게 요청해.
            """.trimIndent()
        )

        // 최초요청 이후에 길찾기를 위한 시나리오(function) 을 추가합니다.
        context.addMessage(openAiChatBotClient.call(context))
        context.scenario = scenario
        println(context.messages.last().content)

        while (true) {
            val input = readlnOrNull() ?: ""
            if (input == "eq") {
                exitProcess(0)
            }

            context.addUserMessage(input)
            context.addMessage(openAiChatBotClient.call(context))

            println(context.messages.last().content)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
