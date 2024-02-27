package com.swapnil.consitenthashing.domain

import org.junit.Assert.*

import org.junit.After
import org.junit.Test

class GenerateHashUseCaseTest {

    private val SUT = GenerateHashUseCase()

    @Test
    fun `test failure value`() {
        assertThrows(AssertionError::class.java) {
            SUT.generateHash("1234")
        }
    }

    @Test
    fun `test success boundary case`() {
        assertEquals("", 292897, SUT.generateHash("aaaaa"))
    }

    @Test
    fun `test success normal case`() {
        assertEquals("", 292897, SUT.generateHash("aaaaa-bbbbb-ccccc-ddddd"))
    }
}