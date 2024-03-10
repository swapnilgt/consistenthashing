package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.node.CreateNodeUseCase
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

class CreateNodeUseCaseTest {

    private val SUT = CreateNodeUseCase(GenerateHashUseCase())

    @Test
    fun `test success case`() {
        val node = SUT.createNode("Title 1")
        assertTrue(node.hashPosition in 0..<HASH_SIZE)
        assertEquals("Title 1", node.title)
        assertTrue(UUID.fromString(node.id) != null)
    }
}