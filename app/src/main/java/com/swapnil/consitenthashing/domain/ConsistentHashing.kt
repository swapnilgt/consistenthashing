package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.NoNodePresentException

internal class ConsistentHashing {

    private val nodes = mutableListOf<Node>()
    private val requests = mutableListOf<Request>()

    fun addRequest(request: Request) {
        if(nodes.isEmpty()) {
            throw NoNodePresentException()
        }
    }

    fun addNode(node: Node) {

    }

}