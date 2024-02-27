package kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.dto

import kr.disdong.virtual.drivers.gpt.api.client.module.drivingdirection.client.Position

data class DrivingDirection(
    val startPosition: Position,
    val endPosition: Position,
    val distance: Int,
    val duration: Int,
)
