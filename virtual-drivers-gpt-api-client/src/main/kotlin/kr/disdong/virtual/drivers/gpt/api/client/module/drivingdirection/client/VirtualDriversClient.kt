package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client

import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.dto.DrivingDirectionEntity
import org.springframework.stereotype.Component

@Component
class VirtualDriversClient(
    private val virtualDriversFeignClient: VirtualDriversFeignClient,
) {

    fun createDrivingDirection(request: DrivingDirectionApiRequest): DrivingDirectionEntity {
        return virtualDriversFeignClient.createDrivingDirection(request).toDrivingDirectionEntity()
    }
}
