package kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.client

import kr.disdong.virtual.drivers.chatbot.api.client.core.config.feign.DefaultFeignConfig
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.dto.DrivingDirection
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "VirtualDriversFeignClient", url = "\${virtual-drivers.base-url}", configuration = [DefaultFeignConfig::class])
interface VirtualDriversFeignClient {
    @GetMapping("/api/v1/translation/address")
    fun getPositionByAddress(
        @RequestParam("address") address: String,
        @RequestParam("translationType") translationType: String,
    ): GetPositionByAddressApiResponse

    @PostMapping("/api/v1/driving-direction")
    fun createDrivingDirection(@RequestBody request: CreateDrivingDirectionApiRequest): CreateDrivingDirectionApiResponse
}

data class GetPositionByAddressApiResponse(
    val data: Position
) {
    fun toPosition(): Position {
        return data
    }
}

enum class AddressTranslationType {
    POSITION,
    JIBUN,
}

data class CreateDrivingDirectionApiRequest(
    val startAddress: String,
    val startLatitude: Double,
    val startLongitude: Double,
    val endAddress: String,
    val endLatitude: Double,
    val endLongitude: Double,
)

data class CreateDrivingDirectionApiResponse(
    val data: Data
) {
    data class Data(
        val startPosition: Position,
        val endPosition: Position,
        val distance: Int,
        val duration: Int,
        val route: List<Position>,
    )

    fun toDrivingDirection(): DrivingDirection {
        return DrivingDirection(
            startPosition = data.startPosition,
            endPosition = data.endPosition,
            distance = data.distance,
            duration = data.duration,
        )
    }
}

data class Position(
    val latitude: Double,
    val longitude: Double,
)
