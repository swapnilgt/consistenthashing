package com.swapnil.consitenthashing.domain

import androidx.annotation.VisibleForTesting
import com.swapnil.consitenthashing.domain.pojo.Node

internal data class CreateNodeUseCase(
    private val generateHashUseCase: GenerateHashUseCase
) {

    fun createNode(title: String): Node {
        val node = Node(title).apply {
            hashPosition = generateHashUseCase.generateHash(id)
        }

        return node
    }

    @VisibleForTesting
    fun createNodeTesting(title: String, hashString: String): Node {
        val node = Node(title).apply {
            hashPosition = generateHashUseCase.generateHash(hashString)
        }

        return node
    }

}