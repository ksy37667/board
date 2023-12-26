package com.board.rest.controller.support

import com.board.config.web.MEDIA_TYPE_APPLICATION_JSON_UTF8
import com.fasterxml.jackson.annotation.JsonAutoDetect
import io.swagger.annotations.ApiModel
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import javax.validation.constraints.Min

abstract class RestSupport {
    protected open fun <T> response(data: T): ResponseEntity<*> {
        return ResponseEntity
            .ok()
            .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
            .body(data)
    }

    protected open fun unauthorized(message: String): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(message)
    }
}


@ApiModel("APIEnvelopPaged")
data class APIEnvelopPage<T>(
    val data: List<T>,
    val page: Int?,
    val size: Int?,
    val totalPage: Int,
    val totalCount: Long,
    val sort: Sort
) {
    constructor(page: Page<T>) : this(
        data = page.content,
        page = page.pageable.pageNumber,
        size = page.size,
        totalPage = page.totalPages,
        totalCount = page.totalElements,
        sort = page.sort
    )
}

interface PageRequest {
    @get: Min(0, message = "page는 0 이상 이여야 합니다.")
    val page: Int

    @get: Min(1, message = "size는 1 이상 이여야 합니다.")
    val size: Int

    fun toPageRequest(): Pageable = org.springframework.data.domain.PageRequest.of(this.page, this.size)
}

interface SingleSortablePageRequest : PageRequest {
    val sort: String?

    override fun toPageRequest(): Pageable =
        org.springframework.data.domain.PageRequest.of(this.page, this.size, parsingSort())

    fun parsingSort(): Sort
}

interface MultipleSortablePageRequest : SingleSortablePageRequest {
    override fun toPageRequest(): Pageable {
        return org.springframework.data.domain.PageRequest.of(this.page, this.size, parsingSort())
    }

    override fun parsingSort(): Sort {
        return this.sort?.trim()
            ?.takeIf { it.isNotBlank() }
            ?.run {
                if (this.contains(",")) {
                    this.split(",").let { convertToSort(it[0], Sort.Direction.fromString(it[1])) }
                } else {
                    convertToSort(this, Sort.Direction.DESC)
                }
            }
            ?: default()
    }

    fun convertToSort(requestedSortField: String, direction: Sort.Direction): Sort
    fun default(): Sort
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@ApiModel("APIEnvelop")
data class APIEnvelop<T>(val data: T)