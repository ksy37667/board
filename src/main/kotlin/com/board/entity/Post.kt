package com.board.entity

import javax.persistence.*

@Entity
@Table(name = "post")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name= "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Status,

    @Column(name = "view")
    var view: Long, // 조회수

    @Column(name = "user_id")
    val userId: Long

): AbstractBaseAuditEntity() {
    enum class Status(
        val description: String
    ){
        NORMAL("일반"),
        NOTICED("공지"),
        DELETED("삭제")
    }
}