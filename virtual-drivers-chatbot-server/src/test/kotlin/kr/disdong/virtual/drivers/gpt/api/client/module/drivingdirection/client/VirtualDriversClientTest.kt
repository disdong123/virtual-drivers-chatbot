package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client

import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.client.GetPositionByAddressRequest
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.client.VirtualDriversClient
import kr.disdong.virtual.drivers.chatbot.api.client.module.drivingdirection.dto.CreateDrivingDirectionRequest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Disabled
class VirtualDriversClientTest {
    @Autowired
    private lateinit var virtualDriversClient: VirtualDriversClient

    @Test
    fun `simple test 1`() {
        println(
            virtualDriversClient.getPositionByAddress(
                GetPositionByAddressRequest(
                    address = "서울특별시 강남구 강남대로 396"
                )
            )
        )
    }

    @Test
    fun `simple test 2`() {
        println(
            virtualDriversClient.createDrivingDirection(
                CreateDrivingDirectionRequest(
                    startAddress = "서울특별시 강남구 강남대로 396",
                    startLatitude = 37.001,
                    startLongitude = 127.001,
                    endAddress = "서울특별시 강남구 역삼로 433",
                    endLatitude = 37.002,
                    endLongitude = 127.002,
                )
            )
        )
    }
}
