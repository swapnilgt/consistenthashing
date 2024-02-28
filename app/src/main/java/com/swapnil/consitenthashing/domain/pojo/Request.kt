package com.swapnil.consitenthashing.domain.pojo

import java.util.UUID

internal class Request(
    title: String
): AbstractHashElement(title) {
    var node: Node? = null
}