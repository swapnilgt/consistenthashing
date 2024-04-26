package com.swapnil.consitenthashing.domain.pojo

import java.util.UUID

internal class Request(
    title: String
): AbstractHashElement(title) {
    var node: Node? = null

    override fun toString(): String {
        return "Request(title='$title', hashPosition=$hashPosition, node=$node)"
    }
}