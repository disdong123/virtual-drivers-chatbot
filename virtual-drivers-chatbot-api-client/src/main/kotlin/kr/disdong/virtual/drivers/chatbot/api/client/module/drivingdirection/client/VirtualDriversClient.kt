package kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.client

import com.fasterxml.jackson.annotation.JsonPropertyDescription
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.dto.CreateDrivingDirectionRequest
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.dto.DrivingDirection
import org.springframework.stereotype.Component

@Component
class VirtualDriversClient(
    private val virtualDriversFeignClient: VirtualDriversFeignClient,
) {

    fun getPositionByAddress(request: GetPositionByAddressRequest): Position {
        return virtualDriversFeignClient.getPositionByAddress(address = request.address, translationType = AddressTranslationType.POSITION.name).toPosition()
    }

    fun createDrivingDirection(request: CreateDrivingDirectionRequest): DrivingDirection {
        return virtualDriversFeignClient.createDrivingDirection(request.toDrivingDirectionApiRequest()).toDrivingDirection()
    }
}

data class GetPositionByAddressRequest(
    @get:JsonPropertyDescription("주소. ex) 서울특별시 강남구 강남대로 396")
    val address: String,
)
