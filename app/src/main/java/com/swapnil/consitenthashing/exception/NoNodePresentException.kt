package com.swapnil.consitenthashing.exception

internal class NoNodePresentException: Exception() {
    override val message: String
        get() = "No node present in the system"
}