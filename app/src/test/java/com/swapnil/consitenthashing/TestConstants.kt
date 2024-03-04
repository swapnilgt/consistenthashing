package com.swapnil.consitenthashing

import com.swapnil.consitenthashing.domain.CreateNodeUseCase
import com.swapnil.consitenthashing.domain.CreateRequestUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request

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

        fun getMockedRequestList(createRequestUseCase: CreateRequestUseCase): List<Request> {
            return listOf(
                createRequestUseCase.createRequestTesting("Request 1", "aaaac"), // 0
                createRequestUseCase.createRequestTesting("Request 1", "aaaaf"), // 1
                createRequestUseCase.createRequestTesting("Request 1", "aaaal"), // 2
                createRequestUseCase.createRequestTesting("Request 1", "aaaam"), // 3
                createRequestUseCase.createRequestTesting("Request 1", "aaaax") // 4
            )
        }

    }

}