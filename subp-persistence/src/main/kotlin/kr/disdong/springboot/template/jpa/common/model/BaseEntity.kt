package kr.disdong.springboot.template.jpa.common.model

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Column
    var isDeleted: Boolean = false,

    @Column
    @CreatedDate
    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column
    @LastModifiedDate
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
)
