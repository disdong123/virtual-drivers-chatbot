package kr.disdong.virtual.drivers.gpt.api.client.module.chatbot.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "openai")
class OpenAiProperties(
    val token: String,
)
