package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.TestConstants
import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.domain.request.AddRequestUseCase
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetNodeIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.exception.NoNodePresentException
import com.swapnil.consitenthashing.exception.NodeNotPresentException
import org.junit.Assert.*
import org.junit.Test

class RemoveNodeUseCaseTest {

    private val generateHashUseCase = GenerateHashUseCase()
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)
    private val createRequestUseCase = CreateRequestUseCase(generateHashUseCase)

    private val SUT = RemoveNodeUseCase(GetRightMostRequestIndexForNewNode())

    private val addRequestUseCase = AddRequestUseCase(
        GetIndexForAddingRequestUseCase(),
        GetNodeIndexForAddingRequestUseCase()
    )

    private val addNodeUseCase = AddNodeUseCase(
        GetIndexForAddingNodeUseCase(),
        GetRightMostRequestIndexForNewNode()
    )

    @Test
    fun `when there are no nodes present, throw NoNodePresentException`() {

        // Setup
        val nodes = mutableListOf<Node>()
        val requests = TestConstants.getMockedRequestList(createRequestUseCase)

        // Execute and assert
        assertThrows(NoNodePresentException::class.java) {
            SUT.removeNode(createNodeUseCase.createNodeTesting("Node 1", "aaaaa"),
                nodes,
                requests)
        }

    }

    @Test
    fun `when the node requested for removal is not present, throw NodeNotPresentException`() {
        // Setup
        val nodes = TestConstants.getMockedNodesList(createNodeUseCase).toMutableList()
        val requests = TestConstants.getMockedRequestList(createRequestUseCase)

        // Execute and assert
        assertThrows(NodeNotPresentException::class.java) {
            SUT.removeNode(createNodeUseCase.createNodeTesting("Node 3", "aaaaa"),
                nodes,
                requests)
        }
    }

    @Test
    fun `when the node removed is the only node present, all the requests will point to null`() {
        // Setup
        val nodes = mutableListOf<Node>()
        val requests = mutableListOf<Request>()

        // Adding a Node.
        addNodeUseCase.addNode(createNodeUseCase.createNodeTesting("Node 1", "aaaam"),
            nodes, requests)

        val request0 = createRequestUseCase.createRequestTesting("Request 0", "aaaac") // 0
        val request1 = createRequestUseCase.createRequestTesting("Request 1", "aaaaf") // 1
        val request2 = createRequestUseCase.createRequestTesting("Request 2", "aaaal") // 2
        val request3 = createRequestUseCase.createRequestTesting("Request 3", "aaaam") // 3
        val request4 = createRequestUseCase.createRequestTesting("Request 4", "aaaax") // 4
        val request5 = createRequestUseCase.createRequestTesting("Request 5", "aaaay") // 5

        addRequestUseCase.addRequest(request0, nodes, requests)
        addRequestUseCase.addRequest(request1, nodes, requests)
        addRequestUseCase.addRequest(request2, nodes, requests)
        addRequestUseCase.addRequest(request3, nodes, requests)
        addRequestUseCase.addRequest(request4, nodes, requests)
        addRequestUseCase.addRequest(request5, nodes, requests)

        // Execute
        SUT.removeNode(nodes[0], nodes, requests)

        // Assert
        assertEquals(0, nodes.size)
        for(request in requests) {
            assertNull(request.node)
        }
    }

    @Test
    fun `when the only two nodes are present and left one is removed, all requests are updated to the left one`() {
        // Setup
        val nodes = mutableListOf<Node>()
        val requests = mutableListOf<Request>()

        // Adding a Node.
        val node1 = createNodeUseCase.createNodeTesting("Node 1", "aaaaa")
        val node2 = createNodeUseCase.createNodeTesting("Node 2", "aaaak")
        addNodeUseCase.addNode(node1, nodes, requests)
        addNodeUseCase.addNode(node2, nodes, requests)

        val request0 = createRequestUseCase.createRequestTesting("Request 0", "aaaac") // 0
        val request1 = createRequestUseCase.createRequestTesting("Request 1", "aaaaf") // 1
        val request2 = createRequestUseCase.createRequestTesting("Request 2", "aaaal") // 2
        val request3 = createRequestUseCase.createRequestTesting("Request 3", "aaaam") // 3
        val request4 = createRequestUseCase.createRequestTesting("Request 4", "aaaax") // 4
        val request5 = createRequestUseCase.createRequestTesting("Request 5", "aaaay") // 5

        addRequestUseCase.addRequest(request0, nodes, requests)
        addRequestUseCase.addRequest(request1, nodes, requests)
        addRequestUseCase.addRequest(request2, nodes, requests)
        addRequestUseCase.addRequest(request3, nodes, requests)
        addRequestUseCase.addRequest(request4, nodes, requests)
        addRequestUseCase.addRequest(request5, nodes, requests)

        // Execute
        SUT.removeNode(node1, nodes, requests)

        // Assert
        assertEquals(1, nodes.size)
        for(request in requests) {
            assertEquals(node2, request.node)
        }
    }

    // TODO -- remove node for which there were no requests present.
    // TODO -- Test case for no requests present.

}