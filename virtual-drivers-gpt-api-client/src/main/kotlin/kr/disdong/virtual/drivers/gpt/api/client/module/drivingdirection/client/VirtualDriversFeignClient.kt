package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client

import kr.disdong.virtual.drivers.gpt.api.client.core.config.feign.DefaultFeignConfig
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.dto.DrivingDirectionEntity
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "VirtualDriversFeignClient", url = "\${virtual-drivers.base-url}", configuration = [DefaultFeignConfig::class])
interface VirtualDriversFeignClient {
    @PostMapping("/api/driving-direction")
    fun createDrivingDirection(@RequestBody request: DrivingDirectionApiRequest): DrivingDirectionApiResponse
}

data class DrivingDirectionApiRequest(
    val startAddress: String,
    val startLatitude: Double,
    val startLongitude: Double,
    val endAddress: String,
    val endLatitude: Double,
    val endLongitude: Double,
)

data class DrivingDirectionApiResponse(
    val data: Data
) {
    data class Data(
        val startPosition: Position,
        val endPosition: Position,
        val distance: Int,
        val duration: Int,
        val route: List<Position>,
    )

    fun toDrivingDirectionEntity(): DrivingDirectionEntity {
        return DrivingDirectionEntity(
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
