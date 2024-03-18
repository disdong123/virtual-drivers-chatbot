package kr.disdong.virtual.drivers.chatbot.server.chatbot.helper

import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.client.OpenAiChatBotClient
import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.dto.ChatContext
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.helper.DrivingDirectionScenario
import org.springframework.stereotype.Component

@Component
class DrivingDirectionChatBot(
    private val openAiChatBotClient: OpenAiChatBotClient,
    private val scenario: DrivingDirectionScenario
) {

    companion object {
        private const val SYSTEM_MESSAGE_ROLE = """
            너는 지금부터 대한민국의 지리에 대해선 누구보다 잘아는 내비게이션 챗봇이야.
            유저는 자신이 찾고 싶은 경로의 출발지와 도착지 주소를 입력할거야. 
            너는 그 주소의 위치(위도/경도 좌표)를 찾아내고, 이를 이용해서 경로를 생성해.
            경로를 생성하면 유저에게 경로에 대한 정보를 알려주고 추가로 궁금한게 있는지 물어봐. 
            유저가 궁금한게 없다면 대화를 종료해.
        """
        private const val SYSTEM_MESSAGE_HELLO = """
            앞으로 유저가 사용할건데, 정말로 대화하듯이 답해줘. 
            이제 유저에게 인사하고 길안내를 위한 출발지, 도착지 주소를 정중하게 요청해.
        """
    }

    fun chat(): ChatContext {
        val context = init()

        // 최초요청 이후에 길찾기를 위한 시나리오(function) 을 추가합니다.
        context.addMessage(openAiChatBotClient.call(context))
        context.scenario = scenario

        return context
    }

    fun chat(context: ChatContext, input: String): ChatContext {
        context.addUserMessage(input)
        context.addMessage(openAiChatBotClient.call(context))
        return context
    }

    private fun init(): ChatContext {
        val context = ChatContext.of()
        context.addSystemMessage(SYSTEM_MESSAGE_ROLE.trimIndent())
        context.addSystemMessage(SYSTEM_MESSAGE_HELLO.trimIndent())
        return context
    }
}
