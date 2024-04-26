package com.swapnil.consitenthashing.domain.node

import androidx.annotation.VisibleForTesting
import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import javax.inject.Inject

internal data class CreateNodeUseCase @Inject constructor(
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

    @VisibleForTesting
    fun createNodeTestingWithHash(title: String, hashPosition: Int): Node {
        val node = Node(title).apply {
            this.hashPosition = hashPosition
        }

        return node
    }

}