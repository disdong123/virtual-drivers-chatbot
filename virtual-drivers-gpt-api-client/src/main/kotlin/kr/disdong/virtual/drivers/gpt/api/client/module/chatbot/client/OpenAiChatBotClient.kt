package kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client

import com.theokanning.openai.completion.CompletionRequest
import com.theokanning.openai.service.OpenAiService
import kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.property.OpenAiProperties
import org.springframework.stereotype.Component

@Component
class OpenAiChatBotClient(
    private val openAiProperties: OpenAiProperties,
) {

    fun call() {
        val service = OpenAiService(openAiProperties.token)
        val completionRequest = CompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .prompt("hello, my name is disdong. what is your name?")
            .echo(true)
            .build()

        service.createCompletion(completionRequest).choices.forEach(System.out::println)
    }
}
