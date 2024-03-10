package com.swapnil.consitenthashing.domain

import com.swapnil.consitenthashing.domain.request.CreateRequestUseCase
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

class CreateRequestUseCaseTest {

    private val SUT = CreateRequestUseCase(GenerateHashUseCase())

    @Test
    fun `test success case`() {
        val request = SUT.createRequest("Title 1")
        assertTrue(request.hashPosition in 0..<HASH_SIZE)
        assertEquals("Title 1", request.title)
        assertTrue(UUID.fromString(request.id) != null)
        assertNull(request.node)
    }
}