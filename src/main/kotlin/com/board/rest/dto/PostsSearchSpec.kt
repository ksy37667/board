package com.board.rest.dto

import com.board.entity.Post
import org.springframework.data.jpa.domain.Specification
import java.time.ZonedDateTime
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class PostsSearchSpec {
    enum class SearchKey(val value: String) {
        STATUS("status"),
        TITLE("title"),
        CONTENT("content"),
        USERID("userId"),
        WRITER("writer"),
        FROM_CREATED_AT("createdAt"),
        TO_CREATED_AT("createdAt")
    }

    companion object {
        fun searchWith (
            searchKey: Map<SearchKey, Any>
        ): Specification<Post> {
            return Specification { root: Root<Post>, _: CriteriaQuery<*>?, builder: CriteriaBuilder ->
                getPredicateWithSearchKey(searchKey, root, builder)
            }
        }

        private fun getPredicateWithSearchKey (
            searchKey: Map<SearchKey, Any>,
            root: Root<Post>,
            cb: CriteriaBuilder
        ): Predicate {
            val predicate = mutableListOf<Predicate>()
            val orPredicate = mutableListOf<Predicate>()

            searchKey.keys.map {
                when(it) {
                    SearchKey.STATUS -> {
                        predicate.add(root.get<String>(it.value).`in`(searchKey[it]))
                    }
                    SearchKey.TITLE,
                    SearchKey.CONTENT,
                    SearchKey.WRITER -> {
                        predicate.add(cb.like(root.get(it.value), "%" + searchKey[it] + "%"))
                    }

                    SearchKey.USERID -> {
                        predicate.add(cb.equal(root.get<Long>(it.value), searchKey[it]))
                    }

                    SearchKey.FROM_CREATED_AT -> {
                        val fromCreatedAt = searchKey[it] as ZonedDateTime
                        predicate.add(
                            cb.greaterThanOrEqualTo(
                                root.get(it.value),
                                fromCreatedAt
                            )
                        )
                    }

                    SearchKey.TO_CREATED_AT -> {
                        val toCreatedAt = searchKey[it] as ZonedDateTime
                        predicate.add(
                            cb.lessThanOrEqualTo(
                                root.get(it.value),
                                toCreatedAt
                            )
                        )
                    }
                }
            }

            return if (orPredicate.isNotEmpty()) {
                cb.and(cb.and(*predicate.toTypedArray()), cb.or(*orPredicate.toTypedArray()))
            } else {
                cb.and(*predicate.toTypedArray())
            }
        }
    }
}


//val title: String?,
//val content: String?,
//val userId: Long,
//val writer: String,
//
//@field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ")
//val createdAt: ZonedDateTime?,
//@field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZZ")
//val modifiedAt: ZonedDateTime?