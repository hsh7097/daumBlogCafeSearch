package com.thomas.daumBlogCafeTest.network.retrofit

import com.thomas.daumBlogCafeTest.BuildConfig
import com.thomas.daumBlogCafeTest.network.retrofit.interceptor.DaumBlogCafeTestHeaderInterceptor
import com.thomas.daumBlogCafeTest.network.retrofit.interceptor.DaumBlogCafeTestLogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class DaumSearchRetrofit {
    companion object {
        private const val TIMEOUT_READ: Long = 15L
        private const val TIMEOUT_CONNECT: Long = 15L
        private const val TIMEOUT_WRITE: Long = 15L
    }


    protected fun <S> getRequestRetrofit(service: Class<S>): S {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(service)
    }


    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .also { clientBuilder ->
                clientBuilder.run {
                    interceptors().clear()
                    addInterceptor(DaumBlogCafeTestHeaderInterceptor())
                    addInterceptor(DaumBlogCafeTestLogInterceptor())
                }
            }
            .build()
    }

}