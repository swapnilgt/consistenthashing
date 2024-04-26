package com.swapnil.consitenthashing.domain.pojo

internal class Node(
    title: String
): AbstractHashElement(title) {
    override fun toString(): String {
        return "Node(title='$title', hashPosition=$hashPosition)"
    }
}