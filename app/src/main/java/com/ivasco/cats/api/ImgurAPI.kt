package com.ivasco.cats.api

import com.ivasco.cats.model.ImgurResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ImgurAPI {

    @GET("search/?q=cats")
    fun getCats(): Single<ImgurResponse>
}