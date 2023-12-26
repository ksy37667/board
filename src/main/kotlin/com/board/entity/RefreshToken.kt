package com.board.entity

import javax.persistence.*


@Entity
@Table(name = "refresh_token")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "value")
    var value: String

): AbstractBaseAuditEntity()