package kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatFunction
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.FunctionExecutor
import com.theokanning.openai.service.OpenAiService
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.property.OpenAiProperties
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.helper.ChatBotScenario
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
        if (response.functionCall == null) {
            return response
        }

        println("경로탐색 중 입니다. 잠시만 기다려주세요.")
        // rate limit 때문에 5초 대기합니다.
        Thread.sleep(5_000L)

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

data class ChatContext(
    val messages: MutableList<ChatMessage> = mutableListOf(),
    var scenario: ChatBotScenario? = null,
    val maxTokens: Int = 1024,
) {
    companion object {
        private val FUNCTION_CALL = ChatCompletionRequest.ChatCompletionRequestFunctionCall("auto")

        fun of(): ChatContext {
            return ChatContext()
        }
    }

    fun getFunctionExecutor(): FunctionExecutor? {
        return scenario?.getScenario()
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
    }

    fun addSystemMessage(message: String) {
        messages.add(ChatMessage(ChatMessageRole.SYSTEM.value(), message))
    }

    fun addUserMessage(message: String) {
        messages.add(ChatMessage(ChatMessageRole.USER.value(), message))
    }

    fun toChatCompletionRequest(model: OpenAiModel): ChatCompletionRequest {
        if (scenario == null) {
            return ChatCompletionRequest.builder()
                .model(model.value)
                .messages(messages)
                .maxTokens(maxTokens)
                .build()
        }

        return ChatCompletionRequest.builder()
            .model(model.value)
            .messages(messages)
            .functions(getFunctionExecutor()!!.functions)
            .functionCall(FUNCTION_CALL)
            .maxTokens(maxTokens)
            .build()
    }
}

data class FunctionExecutorInfo(
    val function: ChatFunction,
    val description: String,
    val name: String,
)

enum class OpenAiModel(val value: String) {
    GPT_3_5_TURBO("gpt-3.5-turbo-0613"),
    BABBAGE_002("babbage-002"),
}
