package com.swapnil.consitenthashing.domain.pojo

import java.util.UUID

internal abstract class AbstractHashElement(
    val title: String,
    val id: String = UUID.randomUUID().toString()
) {
    var hashPosition: Int = -1
}