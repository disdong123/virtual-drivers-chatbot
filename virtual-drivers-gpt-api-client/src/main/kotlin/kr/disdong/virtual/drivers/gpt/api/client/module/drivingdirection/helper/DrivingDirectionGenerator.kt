package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.helper

import com.theokanning.openai.completion.chat.ChatFunction
import com.theokanning.openai.service.FunctionExecutor
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client.ChatContext
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client.OpenAiChatBotClient
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.DrivingDirectionApiRequest
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.VirtualDriversClient
import org.springframework.stereotype.Component

@Component
class DrivingDirectionGenerator(
    private val drivingDirectionClient: VirtualDriversClient,
    private val openAiChatBotClient: OpenAiChatBotClient,
) {

    fun generate(message: String): String? {
        return openAiChatBotClient.call(
            ChatContext.userChatContext(
                message = message,
                functionExecutor = FunctionExecutor(listOf(functionExecutor())),
            )
        )
    }

    private fun functionExecutor() = ChatFunction.builder()
        .name("createDrivingDirection")
        .description("출발지와 도착지 정보를 이용하여 경로를 생성합니다.")
        .executor(DrivingDirectionApiRequest::class.java, drivingDirectionClient::createDrivingDirection)
        .build()
}
