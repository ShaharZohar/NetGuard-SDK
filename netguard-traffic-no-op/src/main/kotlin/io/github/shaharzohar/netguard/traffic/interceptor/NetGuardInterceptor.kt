package io.github.shaharzohar.netguard.traffic.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/** No-op implementation of NetGuardInterceptor. */
class NetGuardInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(chain.request())
}
