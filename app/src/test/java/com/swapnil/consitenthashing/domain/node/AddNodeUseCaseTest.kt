package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedRequestList
import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import org.junit.Assert.*
import org.junit.Test

class AddNodeUseCaseTest {

    private val getIndexForAddingNodeUseCase = GetIndexForAddingNodeUseCase()
    private val getRightMostRequestIndexForNewNode = GetRightMostRequestIndexForNewNode()

    private val SUT = AddNodeUseCase(
        getIndexForAddingNodeUseCase,
        getRightMostRequestIndexForNewNode
    )

    private val generateHashUseCase = GenerateHashUseCase()
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)
    private val createRequestUseCase = CreateRequestUseCase(generateHashUseCase)


    @Test
    fun `when there there are no nodes present and no requests present`() {
        val nodes = mutableListOf<Node>()
        val requests = emptyList<Request>()

        val node = createNodeUseCase.createNodeTesting("Node 1", "aaaaa")

        SUT.addNode(node, nodes, requests)

        assertEquals(1, nodes.size)
        assertEquals(node, nodes[0])
    }

    @Test
    fun `when there are no nodes present and no requests are present, all the requests are updated with the newly added node`() {
        val nodes = mutableListOf<Node>()
        val requests = getMockedRequestList(createRequestUseCase)

        val node = createNodeUseCase.createNodeTesting("Node 1", "aaaaa")

        SUT.addNode(node, nodes, requests)

        assertEquals(1, nodes.size)
        assertEquals(node, nodes[0])
        for(request in requests) {
            assertEquals(node, request.node)
        }
    }
}