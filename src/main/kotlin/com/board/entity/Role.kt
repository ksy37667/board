package com.board.entity

import com.board.entity.enums.RoleType
import javax.persistence.*


@Entity
@Table(name = "Role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name = "name")
    @Enumerated(value = EnumType.STRING)
    val name: RoleType

): AbstractBaseAuditEntity()