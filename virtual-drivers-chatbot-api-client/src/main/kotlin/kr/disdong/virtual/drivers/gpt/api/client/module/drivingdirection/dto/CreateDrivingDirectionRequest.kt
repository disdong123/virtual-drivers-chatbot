package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.dto

import com.fasterxml.jackson.annotation.JsonPropertyDescription
import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.CreateDrivingDirectionApiRequest

data class CreateDrivingDirectionRequest(
    @get:JsonPropertyDescription("출발지 주소. ex) 서울특별시 강남구 강남대로 396")
    val startAddress: String,
    @get:JsonPropertyDescription("출발지 위도. ex) 37.001")
    val startLatitude: Double,
    @get:JsonPropertyDescription("출발지 경도. ex) 127.001")
    val startLongitude: Double,
    @get:JsonPropertyDescription("도착지 주소. ex) 서울특별시 강남구 역삼로 433")
    val endAddress: String,
    @get:JsonPropertyDescription("도착지 위도. ex) 37.002")
    val endLatitude: Double,
    @get:JsonPropertyDescription("도착지 경도. ex) 127.002")
    val endLongitude: Double,
) {
    fun toDrivingDirectionApiRequest() = CreateDrivingDirectionApiRequest(
        startAddress = startAddress,
        startLatitude = startLatitude,
        startLongitude = startLongitude,
        endAddress = endAddress,
        endLatitude = endLatitude,
        endLongitude = endLongitude,
    )
}
