package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedNodesList
import com.swapnil.consitenthashing.TestConstants.Companion.getMockedRequestList
import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.domain.request.AddRequestUseCase
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetNodeIndexForAddingRequestUseCase
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


    // Preparing use case for adding a new request.
    private val getRequestIndexForAddingRequestUseCase = GetIndexForAddingRequestUseCase()
    private val getNodeIndexForAddingRequestUseCase = GetNodeIndexForAddingRequestUseCase()
    private val addRequestUseCase = AddRequestUseCase(
        getRequestIndexForAddingRequestUseCase,
        getNodeIndexForAddingRequestUseCase
    )

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
    fun `when there are no nodes present and requests are present, all the requests are updated with the newly added node`() {
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

    @Test
    fun `when a node is added to the left edge, we see the correct update`() {

        // Setup
        val nodes = getMockedNodesList(createNodeUseCase).toMutableList()

        val requests = mutableListOf<Request>()

        val request0 = createRequestUseCase.createRequestTesting("Request 0", "aaaaa") // 0
        val request1 = createRequestUseCase.createRequestTesting("Request 1", "aaaaf") // 1
        val request2 = createRequestUseCase.createRequestTesting("Request 2", "aaaal") // 2
        val request3 = createRequestUseCase.createRequestTesting("Request 3", "aaaam") // 3
        val request4 = createRequestUseCase.createRequestTesting("Request 4", "aaaax") // 4
        val request5 = createRequestUseCase.createRequestTesting("Request 5", "aaaay") // 4

        addRequestUseCase.addRequest(request0, nodes, requests)
        addRequestUseCase.addRequest(request1, nodes, requests)
        addRequestUseCase.addRequest(request2, nodes, requests)
        addRequestUseCase.addRequest(request3, nodes, requests)
        addRequestUseCase.addRequest(request4, nodes, requests)
        addRequestUseCase.addRequest(request5, nodes, requests)

        // Trigger
        val newNode = createNodeUseCase.createNodeTesting("Node 0", "aaaab")
        SUT.addNode(newNode, nodes, requests)

        // Test
        assertEquals(6, nodes.size)
        assertEquals(newNode, request0.node)
        assertEquals(nodes[2], request1.node)
        assertEquals(nodes[3], request2.node)
        assertEquals(nodes[4], request3.node)
        assertEquals(nodes[5], request4.node)
        assertEquals(newNode, request5.node)

    }

    @Test
    fun `when a node is added to the left edge, we see the correct update -  case 2`() {

        // Setup
        val nodes = mutableListOf<Node>()
        val requests = mutableListOf<Request>()
        SUT.addNode(createNodeUseCase.createNodeTesting("Node 0", "aaaak"), nodes, requests)

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


        // Trigger
        val newNode = createNodeUseCase.createNodeTesting("Node 1", "aaaaa")
        SUT.addNode(newNode, nodes, requests)

        // Test
        assertEquals(2, nodes.size)
        assertEquals(nodes[1], request0.node)
        assertEquals(nodes[1], request1.node)
        assertEquals(nodes[0], request2.node)
        assertEquals(nodes[0], request3.node)
        assertEquals(nodes[0], request4.node)
        assertEquals(nodes[0], request5.node)

    }

    @Test
    fun `when a node is added to the right edge, we see the correct update`() {
        // Setup
        val nodes = mutableListOf(
            createNodeUseCase.createNodeTesting("Node 1", "aaaac"), // 0
            createNodeUseCase.createNodeTesting("Node 2", "aaaaf"), // 1
            createNodeUseCase.createNodeTesting("Node 3", "aaaal"), // 2
            createNodeUseCase.createNodeTesting("Node 4", "aaaat"), // 3
            createNodeUseCase.createNodeTesting("Node 5", "aaaaw") // 4
        )

        val requests = mutableListOf<Request>()

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

        // Trigger
        val newNode = createNodeUseCase.createNodeTesting("Node 6", "aaaaz")
        SUT.addNode(newNode, nodes, requests)

        // Test
        assertEquals(6, nodes.size)
        assertEquals(nodes[0], request0.node)
        assertEquals(nodes[1], request1.node)
        assertEquals(nodes[2], request2.node)
        assertEquals(nodes[3], request3.node)
        assertEquals(newNode, request4.node)
        assertEquals(newNode, request5.node)
    }

    @Test
    fun `when a node is added, all the duplicate items are updated`() {

        // Setup
        val nodes = getMockedNodesList(createNodeUseCase).toMutableList()

        val requests = mutableListOf<Request>()

        val request0 = createRequestUseCase.createRequestTesting("Request 0", "aaaac") // 0
        val request1 = createRequestUseCase.createRequestTesting("Request 1", "aaaaf") // 1
        val request2 = createRequestUseCase.createRequestTesting("Request 2", "aaaal") // 2
        val request31 = createRequestUseCase.createRequestTesting("Request 31", "aaaam") // 31
        val request32 = createRequestUseCase.createRequestTesting("Request 32", "aaaam") // 32
        val request33 = createRequestUseCase.createRequestTesting("Request 33", "aaaao") // 33
        val request34 = createRequestUseCase.createRequestTesting("Request 34", "aaaao") // 34
        val request35 = createRequestUseCase.createRequestTesting("Request 35", "aaaas") // 35
        val request4 = createRequestUseCase.createRequestTesting("Request 4", "aaaax") // 4

        addRequestUseCase.addRequest(request0, nodes, requests)
        addRequestUseCase.addRequest(request1, nodes, requests)
        addRequestUseCase.addRequest(request2, nodes, requests)
        addRequestUseCase.addRequest(request31, nodes, requests)
        addRequestUseCase.addRequest(request32, nodes, requests)
        addRequestUseCase.addRequest(request33, nodes, requests)
        addRequestUseCase.addRequest(request34, nodes, requests)
        addRequestUseCase.addRequest(request35, nodes, requests)
        addRequestUseCase.addRequest(request4, nodes, requests)

        // Trigger
        val newNode = createNodeUseCase.createNodeTesting("Node 6", "aaaao")
        SUT.addNode(newNode, nodes, requests)

        // Test
        assertEquals(6, nodes.size)
        assertEquals(nodes[0], request0.node)
        assertEquals(nodes[1], request1.node)
        assertEquals(nodes[2], request2.node)
        assertEquals(newNode, request31.node)
        assertEquals(newNode, request32.node)
        assertEquals(newNode, request33.node)
        assertEquals(newNode, request34.node)
        assertEquals(nodes[4], request35.node)
        assertEquals(nodes[5], request4.node)
    }
}