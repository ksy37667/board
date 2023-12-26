package com.board.entity

import com.board.entity.enums.SocialType
import com.board.entity.enums.UserStatus
import javax.persistence.*

@Entity
@Table(name = "user")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name = "email")
    val email: String,

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "social_type")
    @Enumerated(value = EnumType.STRING)
    val socialType: SocialType,

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    val status: UserStatus,

    @Column(name = "role_id")
    val roleId: Long

): AbstractBaseAuditEntity()