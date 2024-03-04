package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.pojo.Node
import com.swapnil.consitenthashing.domain.pojo.Request
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
    }
}