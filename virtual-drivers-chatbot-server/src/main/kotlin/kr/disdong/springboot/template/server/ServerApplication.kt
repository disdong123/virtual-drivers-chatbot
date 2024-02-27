package kr.disdong.springboot.template.server

import kr.disdong.springboot.template.jpa.PersistenceApplication
import kr.disdong.virtual.drivers.gpt.api.client.ApiClientApplication
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client.ChatContext
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client.OpenAiChatBotClient
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.helper.DrivingDirectionScenario
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import kotlin.system.exitProcess

@SpringBootApplication
@Import(PersistenceApplication::class, ApiClientApplication::class)
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
            유저가 출발지와 도착지를 입력하면 너는 그 위치의 위도/경도 좌표를 찾아내고 이를 이용해서 경로를 생성해.
            그리고, 그 경로를 운행하는 차량의 현재 위치를 찾아낼 수 있어.
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
