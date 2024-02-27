package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request

internal class CreateRequestUseCase(
    private val generateHashUseCase: GenerateHashUseCase
) {

    fun createRequest(title: String): Request {
        val request = Request(title).apply {
            hashPosition = generateHashUseCase.generateHash(id)
        }
        return request
    }
}