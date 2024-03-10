package com.swapnil.consitenthashing.exception

internal class DuplicateRequestException: Exception() {
    override val message: String
        get() = "Request already present"
}