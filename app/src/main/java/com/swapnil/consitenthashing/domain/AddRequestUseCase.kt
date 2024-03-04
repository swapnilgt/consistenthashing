package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request

internal class AddRequestUseCase(
    private val getIndexForAddingRequestUseCase: GetIndexForAddingRequestUseCase,
    private val getNodeIndexForAddingRequestUseCase: GetNodeIndexForAddingRequestUseCase
) {
    fun addRequest(request: Request, nodes: List<Node>, requests: MutableList<Request>) {
        val nodeIndex = getNodeIndexForAddingRequestUseCase.execute(nodes, request.hashPosition)
        val requestIndex = getIndexForAddingRequestUseCase.execute(requests, request.hashPosition)
        request.node = nodes[nodeIndex]
        requests.add(requestIndex, request)
    }

}
