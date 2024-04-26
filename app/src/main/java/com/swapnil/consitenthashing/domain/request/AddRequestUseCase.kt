package com.swapnil.consitenthashing.domain.request

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.DuplicateRequestException
import javax.inject.Inject

internal class AddRequestUseCase @Inject constructor(
    private val getIndexForAddingRequestUseCase: GetIndexForAddingRequestUseCase,
    private val getNodeIndexForAddingRequestUseCase: GetNodeIndexForAddingRequestUseCase
) {
    @Throws(DuplicateRequestException::class)
    fun addRequest(request: Request, nodes: List<Node>, requests: MutableList<Request>) {
        if(requests.contains(request)) {
            throw DuplicateRequestException()
        }
        val nodeIndex = getNodeIndexForAddingRequestUseCase.execute(nodes, request.hashPosition)
        val requestIndex = getIndexForAddingRequestUseCase.execute(requests, request.hashPosition)
        request.node = nodes[nodeIndex]
        requests.add(requestIndex, request)
    }

}
