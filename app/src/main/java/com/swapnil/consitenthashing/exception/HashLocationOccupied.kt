package com.swapnil.consitenthashing.exception

internal class HashLocationOccupied: Exception() {
    override val message: String
        get() = "Hash location already occupied"
}