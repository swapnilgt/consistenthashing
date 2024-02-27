package com.swapnil.consitenthashing

import com.swapnil.consitenthashing.domain.CreateNodeUseCase
import com.swapnil.consitenthashing.domain.pojo.Node

internal class TestConstants {
    companion object {

        fun getMockedNodesList(createNodeUseCase: CreateNodeUseCase): List<Node> {
            return listOf(
                createNodeUseCase.createNodeTesting("Title 1", "aaaac"), // 0
                createNodeUseCase.createNodeTesting("Title 1", "aaaaf"), // 1
                createNodeUseCase.createNodeTesting("Title 1", "aaaal"), // 2
                createNodeUseCase.createNodeTesting("Title 1", "aaaat"), // 3
                createNodeUseCase.createNodeTesting("Title 1", "aaaax") // 4
            )
        }

    }

}