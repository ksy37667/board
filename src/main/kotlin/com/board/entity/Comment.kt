package com.board.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var post: Post,

    @ManyToOne
    @JoinColumn(name ="user_id")
    var user: User,

    @Column(name = "content")
    var content: String

):AbstractBaseAuditEntity() {

}