package com.board

import com.board.rest.controller.support.APIEnvelop
import com.board.rest.controller.support.APIEnvelopPage
import org.springframework.data.domain.Page

fun <T> Page<T>.wrapPageResponse() = APIEnvelopPage(this)
fun <T> T.wrapDataResponse() = APIEnvelop(this)