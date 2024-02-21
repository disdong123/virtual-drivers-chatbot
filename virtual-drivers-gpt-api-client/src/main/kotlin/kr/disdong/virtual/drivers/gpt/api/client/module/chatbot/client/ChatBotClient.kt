package kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.client

import org.springframework.stereotype.Component

@Component
class ChatBotClient(
    private val openAiChatBotClient: OpenAiChatBotClient,
) {
    fun call() {
        openAiChatBotClient.call()
    }
}
