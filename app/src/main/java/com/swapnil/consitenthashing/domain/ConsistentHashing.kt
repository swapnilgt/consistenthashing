package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.node.AddNodeUseCase
import com.swapnil.consitenthashing.domain.node.CreateNodeUseCase
import com.swapnil.consitenthashing.domain.node.RemoveNodeUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.domain.request.AddRequestUseCase
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetNodeIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.domain.request.RemoveRequestUseCase
import com.swapnil.consitenthashing.exception.HashLocationOccupied
import com.swapnil.consitenthashing.exception.NoNodePresentException
import com.swapnil.consitenthashing.exception.NoRequestsPresentException
import com.swapnil.consitenthashing.exception.RequestNotPresentException
import javax.inject.Inject

internal class ConsistentHashing @Inject constructor(
    private val createNodeUseCase: CreateNodeUseCase,
    private val createRequestUseCase: CreateRequestUseCase,
    private val addNodeUseCase: AddNodeUseCase,
    private val removeNodeUseCase: RemoveNodeUseCase,
    getIndexForAddingRequestUseCase: GetIndexForAddingRequestUseCase,
    getNodeIndexForAddingRequestUseCase: GetNodeIndexForAddingRequestUseCase
) {

    private val nodes = mutableListOf<Node>()
    private val requests = mutableListOf<Request>()

    fun getNodes(): List<Node> {
        return nodes
    }

    fun getRequests(): List<Request> {
        return requests
    }

    private val addRequestUseCase = AddRequestUseCase(
        getIndexForAddingRequestUseCase,
        getNodeIndexForAddingRequestUseCase
    )

    private val remoteRequestUseCase = RemoveRequestUseCase()

    @Throws(NoNodePresentException::class)
    fun addRequest() {
        createRequestUseCase.createRequest("Request ${requests.size + 1}").let {
            addRequestUseCase.addRequest(it, nodes, requests)
        }
    }

    @Throws(RequestNotPresentException::class)
    fun removeRequest(request: Request) {
        remoteRequestUseCase.removeRequest(request, requests)
    }

    @Throws(HashLocationOccupied::class)
    fun addNode() {
        createNodeUseCase.createNode("Node ${nodes.size + 1}").let {
            addNodeUseCase.addNode(it, nodes, requests)
        }
    }

    @Throws(NoNodePresentException::class,
        RequestNotPresentException::class,
        NoRequestsPresentException::class)
    fun removeNode(node: Node) {
        removeNodeUseCase.removeNode(node, nodes, requests)
    }

}