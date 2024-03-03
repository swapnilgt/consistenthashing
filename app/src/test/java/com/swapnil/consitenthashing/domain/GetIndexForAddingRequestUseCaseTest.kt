package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedRequestList
import org.junit.Assert.*
import org.junit.Test

class GetIndexForAddingRequestUseCaseTest {

    private val SUT = GetIndexForAddingRequestUseCase()

    private val generateHashUseCase = GenerateHashUseCase()
    private val createRequestUseCase = CreateRequestUseCase(generateHashUseCase)

    @Test
    fun `when the list is empty, return 0`() {
        assertEquals(0, SUT.execute(emptyList(), 1))
    }

    @Test
    fun `when the list has only one element and the new element has hashPosition smaller than the existing element, return 0`() {
        assertEquals(0,
            SUT.execute(listOf(createRequestUseCase.createRequest("Request 1")),
                0))
    }

    @Test
    fun `when the list has only one element and the new element has hasPosition greater than the existing element, return 1`() {
        assertEquals(1,
            SUT.execute(listOf(createRequestUseCase.createRequest("Request 1")),
                HASH_SIZE - 1))
    }

    @Test
    fun `when the list has only one element and the new element has hasPosition same to the existing position, then return 0`() {
        assertEquals(0,
            SUT.execute(listOf(createRequestUseCase.createRequestTesting("Request 1", "aaaaa")),
                generateHashUseCase.generateHash("aaaaa")))
        assertEquals(0,
            SUT.execute(listOf(createRequestUseCase.createRequestTesting("Request 1", "aaaaz")),
                generateHashUseCase.generateHash("aaaaz")))
    }

    @Test
    fun `when the new element hashPosition is smaller than the first element, return 0`() {
        val list = getMockedRequestList(createRequestUseCase)
        assertEquals(0, SUT.execute(list, generateHashUseCase.generateHash("aaaaa")))
    }

    @Test
    fun `when the new element hashPosition is greater than the last element, return appropriate index`() {
        val list = getMockedRequestList(createRequestUseCase)
        assertEquals(list.size, SUT.execute(list, generateHashUseCase.generateHash("aaaaz")))
    }

    @Test
    fun `when the list has more than one element, we return the appropriate element`() {
        val list = getMockedRequestList(createRequestUseCase)
        assertEquals(3, SUT.execute(list, generateHashUseCase.generateHash("aaaam")))

    }

}