package com.swapnil.consitenthashing.domain.request

import com.swapnil.consitenthashing.domain.pojo.Request
import com.swapnil.consitenthashing.exception.RequestNotPresentException

internal class RemoveRequestUseCase {
    @Throws(RequestNotPresentException::class)
    fun removeRequest(request: Request, requests: MutableList<Request>) {
        if(requests.contains(request)) {
            requests.remove(request)
        } else {
            throw RequestNotPresentException()
        }
    }
}