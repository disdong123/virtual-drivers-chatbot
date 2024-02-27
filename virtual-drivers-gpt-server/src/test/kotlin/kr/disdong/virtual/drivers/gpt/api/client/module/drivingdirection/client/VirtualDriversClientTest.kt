package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client

import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.dto.CreateDrivingDirectionRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VirtualDriversClientTest {
    @Autowired
    private lateinit var virtualDriversClient: VirtualDriversClient

    @Test
    fun ab() {
        println(
            virtualDriversClient.getPositionByAddress(
                GetPositionByAddressRequest(
                    address = "서울특별시 강남구 강남대로 396"
                )
            )
        )
    }

    @Test
    fun a() {
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
