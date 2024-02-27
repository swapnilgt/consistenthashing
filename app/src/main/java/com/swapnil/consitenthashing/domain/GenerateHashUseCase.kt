package com.swapnil.consitenthashing.domain

import kotlin.math.pow

private const val G = 31
val HASH_SIZE = 2.0.pow(20.0).toInt()

internal class GenerateHashUseCase {

    fun generateHash(input: String): Int {
        // Assert that the string is greater of length >= 5
        assert(input.length >= 5) { "Input string should be greater than 5 characters" }

        // Taking the first 8 character of the id to generate the hash string.
        val hashSubString = input.substring(0, 5)
        var hashInt = 0
        for(c in hashSubString) {
            hashInt = G * hashInt + c.code
        }
        return hashInt % HASH_SIZE
    }
}