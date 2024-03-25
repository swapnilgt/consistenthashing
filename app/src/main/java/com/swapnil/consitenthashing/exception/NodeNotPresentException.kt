package com.swapnil.consitenthashing.exception

internal class NodeNotPresentException: Exception() {
    override val message: String
        get() = "Node not present in the list"
}