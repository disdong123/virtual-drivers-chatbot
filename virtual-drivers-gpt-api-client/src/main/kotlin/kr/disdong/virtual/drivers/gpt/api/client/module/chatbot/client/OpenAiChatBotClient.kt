package kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.FunctionExecutor
import com.theokanning.openai.service.OpenAiService
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.property.OpenAiProperties
import org.springframework.stereotype.Component

@Component
class OpenAiChatBotClient(
    private val openAiProperties: OpenAiProperties,
) {
    private val openAiService = OpenAiService(openAiProperties.token)

    fun call(chatContext: ChatContext): String? {
        val responseMessage = openAiService.createChatCompletion(chatContext.toChatCompletionRequest()).choices.first().message
        val functionCall = responseMessage.functionCall
        if (functionCall != null) {
            println("처리중 .... $responseMessage")

            val message = chatContext.functionExecutor.executeAndConvertToMessageHandlingExceptions(functionCall)

            println("?? $message")
            if (message.name == "error") {
                throw RuntimeException("function call error")
            }

            // call(chatContext.child(listOf(responseMessage, message)))
        }

        return responseMessage.content
    }
}

data class ChatContext(
    val model: String,
    val messages: List<ChatMessage>,
    val functionExecutor: FunctionExecutor,
    val maxTokens: Int = 1024,
) {
    companion object {
        private val FUNCTION_CALL = ChatCompletionRequest.ChatCompletionRequestFunctionCall("auto")
        private const val DEFAULT_MODEL = "gpt-3.5-turbo-0613"

        fun userChatContext(model: String = DEFAULT_MODEL, message: String, functionExecutor: FunctionExecutor): ChatContext {
            return ChatContext(
                model = model,
                messages = listOf(ChatMessage(ChatMessageRole.USER.value(), message)),
                functionExecutor = functionExecutor,
            )
        }
    }

    fun child(messages: List<ChatMessage>): ChatContext {
        return ChatContext(
            model = model,
            messages = messages,
            functionExecutor = functionExecutor,
        )
    }

    fun toChatCompletionRequest(): ChatCompletionRequest {
        return ChatCompletionRequest.builder()
            .model(model)
            .messages(messages)
            .functions(functionExecutor.functions)
            .functionCall(FUNCTION_CALL)
            .maxTokens(maxTokens)
            .build()
    }
}
