package kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.dto

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.FunctionExecutor
import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.client.OpenAiModel
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.helper.ChatBotScenario

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
