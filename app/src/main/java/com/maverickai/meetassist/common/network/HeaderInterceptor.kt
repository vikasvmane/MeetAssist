package com.maverickai.meetassist.common.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(request = addHeaders(chain.request()))
    }

    private fun addHeaders(request: Request): Request {
        return request.newBuilder().addHeader(
            AUTHORIZATION, BEARER.plus(CLIENT_KEY)
        ).addHeader(CONTENT_TYPE, APPLICATION_JSON).build()
    }

    companion object {
        const val CLIENT_KEY = "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"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
    }
}