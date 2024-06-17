package com.swapnil.consitenthashing.domain.request

import androidx.annotation.VisibleForTesting
import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.pojo.Request
import javax.inject.Inject

internal class CreateRequestUseCase @Inject constructor(
    private val generateHashUseCase: GenerateHashUseCase
) {

    fun createRequest(title: String): Request {
        val request = Request(title).apply {
            hashPosition = generateHashUseCase.generateHash(id)
        }
        return request
    }

    @VisibleForTesting
    fun createRequestTesting(title: String, hashString: String): Request {
        val request = Request(title).apply {
            hashPosition = generateHashUseCase.generateHash(hashString)
        }

        return request
    }

    @VisibleForTesting
    fun createRequestTestingWithHash(title: String, hashPosition: Int): Request {
        val request = Request(title).apply {
            this.hashPosition = hashPosition
        }

        return request
    }

    @VisibleForTesting
    private fun myTestMethod(): Int {
        return 0
    }
}