package com.swapnil.consitenthashing.domain.node

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedRequestList
import com.swapnil.consitenthashing.domain.GenerateHashUseCase
import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import com.swapnil.consitenthashing.exception.NoRequestsPresentException
import org.junit.Assert.*
import org.junit.Test

class GetRightMostRequestIndexForNewNodeTest {

    private val SUT = GetRightMostRequestIndexForNewNode()

    private val generateHashUseCase = GenerateHashUseCase()
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)
    private val createRequestUseCase = CreateRequestUseCase(generateHashUseCase)

    @Test
    fun `when the requests list is empty, throw NoRequestsPresentException`() {
        assertThrows(NoRequestsPresentException::class.java) {
            SUT.execute(emptyList(), createNodeUseCase.createNodeTesting("Node 1",
                "aaaaa"))
        }
    }

    @Test
    fun `when the request list has single element and the element has hash position larger than the incoming node, return 0`() {
        val request = createRequestUseCase.createRequestTesting("Request 1", "aaaal")
        val requests = mutableListOf(request)

        val node1 = createNodeUseCase.createNodeTesting("Node 1", "aaaaa")
        val node2 = createNodeUseCase.createNodeTesting("Node 1", "aaaaz")

        assertEquals(0, SUT.execute(requests, node1))
        assertEquals(0, SUT.execute(requests, node2))
    }

    @Test
    fun `when the the new node has hash smaller than the first element in the requests list return size-1`() {
        val requests = getMockedRequestList(createRequestUseCase)
        val node1 = createNodeUseCase.createNodeTesting("Node 1", "aaaaa")

        assertEquals(requests.size - 1, SUT.execute(requests, node1))

    }

    @Test
    fun `when the the new node has hash equal to  the first element in the requests list, return 0`() {
        val requests = getMockedRequestList(createRequestUseCase)
        val node1 = createNodeUseCase.createNodeTesting("Node 1", "aaaac")

        assertEquals(0, SUT.execute(requests, node1))
    }

    @Test
    fun `when the new node has hash larger than equal to the last element in the requests list, return size - 1`() {
        val requests = getMockedRequestList(createRequestUseCase)
        val node1 = createNodeUseCase.createNodeTesting("Node 1", "aaaaz")
        val node2 = createNodeUseCase.createNodeTesting("Node 1", "aaaax")

        assertEquals(requests.size - 1, SUT.execute(requests, node1))
        assertEquals(requests.size - 1, SUT.execute(requests, node2))
    }

    @Test
    fun `when the new node has hash between two element, then the index of the right element is returned`() {
        val requests = getMockedRequestList(createRequestUseCase)
        val node1 = createNodeUseCase.createNodeTesting("Node 1", "aaaah")
        val node2 = createNodeUseCase.createNodeTesting("Node 2", "aaaal")
        val node3 = createNodeUseCase.createNodeTesting("Node 3", "aaaam")
        val node4 = createNodeUseCase.createNodeTesting("Node 4", "aaaap")
        val node5 = createNodeUseCase.createNodeTesting("Node 5", "aaaad")
        val node6 = createNodeUseCase.createNodeTesting("Node 6", "aaaaf")
        val node7 = createNodeUseCase.createNodeTesting("Node 7", "aaaax")

        assertEquals(1, SUT.execute(requests, node1))
        assertEquals(2, SUT.execute(requests, node2))
        assertEquals(3, SUT.execute(requests, node3))
        assertEquals(3, SUT.execute(requests, node4))
        assertEquals(0, SUT.execute(requests, node5))
        assertEquals(1, SUT.execute(requests, node6))
        assertEquals(4, SUT.execute(requests, node7))
    }
}