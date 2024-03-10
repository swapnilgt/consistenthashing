package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedNodesList
import com.swapnil.consitenthashing.domain.node.CreateNodeUseCase
import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.domain.request.AddRequestUseCase
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.domain.request.GetNodeIndexForAddingRequestUseCase
import com.swapnil.consitenthashing.exception.DuplicateRequestException
import com.swapnil.consitenthashing.exception.NoNodePresentException
import org.junit.Assert.*
import org.junit.Test

class AddRequestUseCaseTest {

    private val generateHashUseCase = GenerateHashUseCase()
    private val createRequestUseCase = CreateRequestUseCase(generateHashUseCase)
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)
    private val getRequestIndexForAddingRequestUseCase = GetIndexForAddingRequestUseCase()
    private val getNodeIndexForAddingRequestUseCase = GetNodeIndexForAddingRequestUseCase()
    private val SUT = AddRequestUseCase(
        getRequestIndexForAddingRequestUseCase,
        getNodeIndexForAddingRequestUseCase
    )

    @Test
    fun `addRequest() when a request is added with an empty nodes list, we get an exception`() {
        // Arrange
        val request = createRequestUseCase.createRequestTesting("Request 1", "aaaaa")
        val nodes = mutableListOf<Node>()
        val requests = mutableListOf<Request>()

        // Assert
        assertThrows(NoNodePresentException::class.java) {
            SUT.addRequest(request, nodes, requests)
        }
    }

    @Test
    fun `addRequest() when a single element is present in the array`() {
        // Arrange
        val request = createRequestUseCase.createRequestTesting("Request 1", "aaaaa")
        val requests = mutableListOf<Request>()

        val nodes = mutableListOf(createNodeUseCase.createNodeTesting("Node 1",
            "aaaaa"))

        // Act
        SUT.addRequest(request, nodes, requests)

        // Assert
        assertEquals(1, requests.size)
        assertEquals(request, requests[0])
        assertEquals(nodes[0], requests[0].node)
    }

    @Test
    fun `addRequest() when request is already present, throw duplicate request exception`() {
        // Arrange
        val request = createRequestUseCase.createRequestTesting("Request 1", "aaaaa")
        val requests = mutableListOf<Request>()

        val nodes = mutableListOf(createNodeUseCase.createNodeTesting("Node 1",
            "aaaaa"))

        // Act
        SUT.addRequest(request, nodes, requests)

        // Assert
        assertThrows(DuplicateRequestException::class.java) {
            SUT.addRequest(request, nodes, requests)
        }
    }

    @Test
    fun `addRequest() element lands at the correct location`() {

        // Setup ..
        val nodes = getMockedNodesList(createNodeUseCase)
        val requests = mutableListOf<Request>()

        val request0 = createRequestUseCase.createRequestTesting("Request 0", "aaaaa") // 0
        val request1 = createRequestUseCase.createRequestTesting("Request 1", "aaaac") // 1
        val request2 = createRequestUseCase.createRequestTesting("Request 2", "aaaaf") // 2
        val request3 = createRequestUseCase.createRequestTesting("Request 3", "aaaal") // 3
        val request41 = createRequestUseCase.createRequestTesting("Request 41", "aaaam") // 4
        val request42 = createRequestUseCase.createRequestTesting("Request 42", "aaaam") // 5
        val request5 = createRequestUseCase.createRequestTesting("Request 5", "aaaax") // 6
        val request6 = createRequestUseCase.createRequestTesting("Request 6", "aaaay") // 7


        // Act
        SUT.addRequest(request5, nodes, requests)
        SUT.addRequest(request3, nodes, requests)
        SUT.addRequest(request41, nodes, requests)
        SUT.addRequest(request1, nodes, requests)
        SUT.addRequest(request2, nodes, requests)
        // Adding duplicate again ....
        SUT.addRequest(request42, nodes, requests)
        // Adding boundary cases.
        SUT.addRequest(request0, nodes, requests)
        SUT.addRequest(request6, nodes, requests)

        // Assert
        assertEquals(8, requests.size)

        assertEquals(request6, requests[0])
        assertEquals(nodes[0], request6.node)

        assertEquals(request0, requests[1])
        assertEquals(nodes[0], request0.node)

        assertEquals(request1, requests[2])
        assertEquals(nodes[0], request1.node)

        assertEquals(request2, requests[3])
        assertEquals(nodes[1], request2.node)

        assertEquals(request3, requests[4])
        assertEquals(nodes[2], request3.node)

        assertEquals(request42, requests[5])
        assertEquals(nodes[3], request41.node)

        assertEquals(request41, requests[6])
        assertEquals(nodes[3], request42.node)

        assertEquals(request5, requests[7])
        assertEquals(nodes[4], request5.node)
    }
}