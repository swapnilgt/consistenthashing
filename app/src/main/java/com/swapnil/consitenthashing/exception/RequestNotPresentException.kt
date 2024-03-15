package com.swapnil.consitenthashing.exception

internal class RequestNotPresentException: Exception() {
    override val message: String
        get() = "Request not present"
}