package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client

import org.springframework.stereotype.Component

@Component
class VirtualDriversClient(
    private val virtualDriversFeignClient: VirtualDriversFeignClient,
) {

    fun createDrivingDirection(request: DrivingDirectionApiRequest): DrivingDirectionApiResponse {
        return virtualDriversFeignClient.createDrivingDirection(request)
    }
}
