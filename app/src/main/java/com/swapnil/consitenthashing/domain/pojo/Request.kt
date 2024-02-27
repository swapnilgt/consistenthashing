package com.swapnil.consitenthashing.domain.pojo

import java.util.UUID

internal data class Request(
    val title: String,
    val id: String = UUID.randomUUID().toString()
) {
    var node: Node? = null
    var hashPosition: Int = -1
}