package com.ivasco.cats.di

import com.ivasco.cats.api.ImgurAPI
import com.ivasco.cats.service.ImgurService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.imgur.com/3/gallery/"

@Module
class ApiModule {

    private val requestInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Client-ID 1ceddedc03a5d71")
            .build()
        chain.proceed(newRequest)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor).build()

    @Provides
    fun providesImgurApi(): ImgurAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ImgurAPI::class.java)
    }

    @Provides
    fun providesImgurnService(): ImgurService =
        ImgurService()
}