package com.board.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
@JsonIgnoreProperties(value = [
    "createdAt, modifiedAt"
], allowGetters = true)
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractBaseAuditEntity {
    @Column(name = "created_at", columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ", timezone = "Asia/Seoul")
    var createdAt: ZonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Seoul"))

    @Column(name = "modified_at", columnDefinition = "datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ", timezone = "Asia/Seoul")
    var modifiedAt: ZonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Seoul"))

    @PrePersist
    fun prePersist() {
        this.createdAt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Seoul"))
        this.modifiedAt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Seoul"))
    }

    @PreUpdate
    fun preUpdate() {
        this.modifiedAt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Seoul"))
    }
}