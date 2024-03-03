package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedNodesList
import com.swapnil.consitenthashing.exception.NoNodePresentException
import org.junit.Assert.*
import org.junit.Test

/**
 * This use case is responsible for finding the correct index for the new Request to be inserted.
 */
class GetNodeIndexForAddRequestUseCaseTest {

    private val SUT = GetIndexForAddingRequestUseCase()
    private val generateHashUseCase = GenerateHashUseCase()
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)

    @Test
    fun `when there is no element present, throws NoNodePresentException`() {
        assertThrows(NoNodePresentException::class.java) {
            SUT.execute(emptyList(), 0)
        }
    }

    @Test
    fun `when there is a single element present, then return 0`() {
        assertEquals(0, SUT.execute(listOf(createNodeUseCase.createNode("Title 1")),
            0))
    }

    @Test
    fun `when the new element has hash position greater than the last element, then return 0`() {
        val list = getMockedNodesList(createNodeUseCase)
        assertEquals(0, SUT.execute(list, HASH_SIZE - 1))
    }

    @Test
    fun `when the new element has hash position smaller than the last element, then return 0`() {
        val list = getMockedNodesList(createNodeUseCase)
        assertEquals(0, SUT.execute(list, 0))
        assertEquals(0, SUT.execute(list, generateHashUseCase.generateHash("aaaac")))
    }

    @Test
    fun `when the new element has hash position in between the elements, then return appropriate index`() {
        val list = getMockedNodesList(createNodeUseCase)
        assertEquals(3, SUT.execute(list, generateHashUseCase.generateHash("aaaam")))
    }

    @Test
    fun `when the new element has hash position in equal to one of the elements, then return appropriate index`() {
        val list = getMockedNodesList(createNodeUseCase)
        assertEquals(3, SUT.execute(list, generateHashUseCase.generateHash("aaaat")))
        assertEquals(0, SUT.execute(list, generateHashUseCase.generateHash("aaaac")))
        assertEquals(4, SUT.execute(list, generateHashUseCase.generateHash("aaaax")))
    }

}