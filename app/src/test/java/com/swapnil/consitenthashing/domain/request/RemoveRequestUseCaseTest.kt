package com.swapnil.consitenthashing.domain.request

import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.node.CreateNodeUseCase
import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.RequestNotPresentException
import org.junit.Assert.*
import org.junit.Test

class RemoveRequestUseCaseTest {

    private val SUT = RemoveRequestUseCase()

    private val generateHashUseCase = GenerateHashUseCase()
    private val createRequestUseCase = CreateRequestUseCase(generateHashUseCase)
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)
    private val addRequestUseCase = AddRequestUseCase(
        GetIndexForAddingRequestUseCase(),
        GetNodeIndexForAddingRequestUseCase()
    )

    @Test
    fun `remove when element is present`() {
        // Arrange
        val request = createRequestUseCase.createRequestTesting("Request 1", "aaaaa")
        val requests = mutableListOf<Request>()

        val nodes = mutableListOf(createNodeUseCase.createNodeTesting("Node 1",
            "aaaaa"))
        addRequestUseCase.addRequest(request, nodes, requests)

        // Act
        SUT.removeRequest(request, requests)

        // Assert
        assertEquals(0, requests.size)
    }

    @Test
    fun `remove when element is not present`() {
        // Arrange
        val request = createRequestUseCase.createRequestTesting("Request 1", "aaaaa")
        val notPresentRequest = createRequestUseCase.createRequestTesting("Request 2", "aaaab")
        val requests = mutableListOf<Request>()

        val nodes = mutableListOf(createNodeUseCase.createNodeTesting("Node 1",
            "aaaaa"))
        addRequestUseCase.addRequest(request, nodes, requests)
        // Act
        assertThrows(
            RequestNotPresentException::class.java
        ) {
            SUT.removeRequest(notPresentRequest, requests)
        }

    }

}