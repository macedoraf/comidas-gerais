package br.com.rafa_macedo.comidas_gerais.core.data

import br.com.rafa_macedo.comidas_gerais.core.BuildInfoProvider
import okhttp3.Interceptor
import okhttp3.Response

class CredentialInterceptor(private val buildInfoProvider: BuildInfoProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .addHeader("x-api-key", buildInfoProvider.apiKey)
            .build().run {
                chain.proceed(this)
            }
    }
}