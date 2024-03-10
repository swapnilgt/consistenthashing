package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.node.CreateNodeUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.domain.request.AddRequestUseCase
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetNodeIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.exception.NoNodePresentException

internal class ConsistentHashing(
    private val createNodeUseCase: CreateNodeUseCase,
    private val createRequestUseCase: CreateRequestUseCase,
    private val getIndexForAddingRequestUseCase: GetIndexForAddingRequestUseCase,
    private val getNodeIndexForAddingRequestUseCase: GetNodeIndexForAddingRequestUseCase
) {

    private val nodes = mutableListOf<Node>()
    private val requests = mutableListOf<Request>()

    private val addRequestUseCase = AddRequestUseCase(
        getIndexForAddingRequestUseCase,
        getNodeIndexForAddingRequestUseCase
    )

    @Throws(NoNodePresentException::class)
    fun addRequest(request: Request) {
        addRequestUseCase.addRequest(request, nodes, requests)
    }

}