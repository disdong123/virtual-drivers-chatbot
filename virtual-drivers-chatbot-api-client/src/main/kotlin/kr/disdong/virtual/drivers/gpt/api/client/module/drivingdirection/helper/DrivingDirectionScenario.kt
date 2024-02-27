package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.theokanning.openai.completion.chat.ChatFunction
import com.theokanning.openai.service.FunctionExecutor
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.GetPositionByAddressRequest
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.VirtualDriversClient
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.dto.CreateDrivingDirectionRequest
import org.springframework.stereotype.Component

interface ChatBotScenario {
    fun getScenario(): FunctionExecutor
}

@Component
class DrivingDirectionScenario(
    private val objectMapper: ObjectMapper,
    private val virtualDriversClient: VirtualDriversClient,
) : ChatBotScenario {

    override fun getScenario(): FunctionExecutor {
        return FunctionExecutor(listOf(getPositionByAddress(), createDrivingDirectionChatFunction()), objectMapper)
    }

    private fun getPositionByAddress() = ChatFunction.builder()
        .name("getPositionByAddress")
        .description("주소정보를 이용하여 위도, 경도 좌표를 찾아옵니다.")
        .executor(GetPositionByAddressRequest::class.java, virtualDriversClient::getPositionByAddress)
        .build()

    private fun createDrivingDirectionChatFunction() = ChatFunction.builder()
        .name("createDrivingDirectionChatFunction")
        .description("출발지 위/경도 좌표와 도착지 위/경도 좌표를 이용하여 경로를 생성합니다.")
        .executor(CreateDrivingDirectionRequest::class.java, virtualDriversClient::createDrivingDirection)
        .build()
}
