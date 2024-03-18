package kr.disdong.virtual.drivers.chatbot.server.chatbot.helper

import kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.dto.ChatContext
import org.springframework.stereotype.Component

/**
 * TODO 어떻게 출력할지?
 */
interface ChatPrinter {

    fun print(context: ChatContext)

    fun print(message: String)
}

@Component
class DefaultChatPrinter : ChatPrinter {

    override fun print(context: ChatContext) {
        println(context.messages.last().content)
    }

    override fun print(message: String) {
        println(message)
    }
}
