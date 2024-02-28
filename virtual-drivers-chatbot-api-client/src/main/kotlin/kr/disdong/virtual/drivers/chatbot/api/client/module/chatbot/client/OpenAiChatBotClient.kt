package kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.client

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.dto.ChatContext
import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.property.OpenAiProperties
import org.springframework.stereotype.Component

@Component
class OpenAiChatBotClient(
    private val openAiProperties: OpenAiProperties,
) {
    private val openAiService = OpenAiService(openAiProperties.token)

    fun call(chatContext: ChatContext): ChatMessage {
        if (chatContext.scenario == null) {
            return call(chatContext.messages, chatContext.maxTokens)
        }

        val response = openAiService.createChatCompletion(chatContext.toChatCompletionRequest(OpenAiModel.GPT_3_5_TURBO)).choices.first().message
        // 시나리오 종료로 판단하고 종료합니다.
        if (response.functionCall == null) {
            return response
        }

        println("경로탐색 중 입니다. 잠시만 기다려주세요.")
        // rate limit 을 회피하기 위해 잠시 대기합니다.
        Thread.sleep(10_000L)

        val message = chatContext.getFunctionExecutor()!!.executeAndConvertToMessageHandlingExceptions(response.functionCall)

        chatContext.addMessage(message)
        return if (message.name == "error") {
            chatContext.scenario = null
            call(chatContext)
        } else {
            call(chatContext)
        }
    }

    /**
     * function call 없이 요청합니다.
     */
    private fun call(messages: MutableList<ChatMessage>, maxTokens: Int): ChatMessage {
        val request = ChatCompletionRequest.builder()
            .model(OpenAiModel.GPT_3_5_TURBO.value)
            .messages(messages)
            .n(1)
            .maxTokens(maxTokens)
            .logitBias(HashMap<String, Int>())
            .build()

        return openAiService.createChatCompletion(request).choices.first().message
    }
}

enum class OpenAiModel(val value: String) {
    GPT_3_5_TURBO("gpt-3.5-turbo-0613"),
    BABBAGE_002("babbage-002"),
}
