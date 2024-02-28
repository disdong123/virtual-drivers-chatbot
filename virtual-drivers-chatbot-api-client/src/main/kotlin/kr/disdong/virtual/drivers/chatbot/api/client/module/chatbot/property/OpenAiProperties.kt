package kr.disdong.virtual.drivers.chatbot.api.client.module.chatbot.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "openai")
class OpenAiProperties(
    val token: String,
)
