package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.TestConstants.Companion.getMockedNodesList
import com.swapnil.consitenthashing.exception.HashLocationOccupied
import org.junit.Assert.*
import org.junit.Test

class GetLeftIndexUseCaseTest {

    private val SUT = GetLeftIndexUseCase()
    private val generateHashUseCase = GenerateHashUseCase()
    private val createNodeUseCase = CreateNodeUseCase(generateHashUseCase)

    @Test
    fun `when the list is empty, return position 0`() {
        assertEquals(0, SUT.execute(emptyList(), 1))
    }

    @Test
    fun `when the list has only one element and the new element has hashPosition smaller than the existing element, return position 0`() {
        assertEquals(0, SUT.execute(listOf(createNodeUseCase.createNodeTesting("Title 1", "aaaaa")), 0))
    }

    @Test
    fun `when the list has only one element and the new element has hashPosition greater than the existing element, return position 0`() {
        assertEquals(1, SUT.execute(listOf(createNodeUseCase.createNodeTesting("Title 1", "aaaaa")), HASH_SIZE - 1))
    }

    @Test
    fun `when the incoming element has the position that is smaller than the first element, the return index is 0`() {
        // Creating the mocked data ..
        val myList = getMockedNodesList(createNodeUseCase)
        assertEquals(0, SUT.execute(myList, generateHashUseCase.generateHash("aaaaa")))
    }

    @Test
    fun `when the incoming element is somewhere in between appropriate index is set`() {
        // Creating the mocked data ..
        val myList = getMockedNodesList(createNodeUseCase)
        assertEquals(3, SUT.execute(myList, generateHashUseCase.generateHash("aaaam")))
    }

    @Test
    fun `when the incoming element is already present, then we should get HashLocationOccupied`() {
        // Creating the mocked data ..
        val myList = getMockedNodesList(createNodeUseCase)
        assertThrows(HashLocationOccupied::class.java) {
            SUT.execute(myList, generateHashUseCase.generateHash("aaaal"))
        }
        assertThrows(HashLocationOccupied::class.java) {
            SUT.execute(myList, generateHashUseCase.generateHash("aaaac"))
        }
        assertThrows(HashLocationOccupied::class.java) {
            SUT.execute(myList, generateHashUseCase.generateHash("aaaax"))
        }
    }

}