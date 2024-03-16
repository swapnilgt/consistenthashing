package com.swapnil.consitenthashing.exception

internal class NoRequestsPresentException : Exception(){
    override val message: String
        get() = "No requests present in the system"

}