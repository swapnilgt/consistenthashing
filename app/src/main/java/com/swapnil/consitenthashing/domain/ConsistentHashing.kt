package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.NoNodePresentException

internal class ConsistentHashing {

    private val nodes = mutableListOf<Node>()
    private val requests = mutableListOf<Request>()
    private val addRequestUseCase = AddRequestUseCase()

    fun addRequest(request: Request) {

    }

    fun addNode(node: Node) {

    }

}