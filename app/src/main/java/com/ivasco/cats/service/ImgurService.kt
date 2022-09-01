package com.ivasco.cats.service

import com.ivasco.cats.di.DaggerApiComponent
import com.ivasco.cats.api.ImgurAPI
import javax.inject.Inject

class ImgurService {

    @Inject
    lateinit var imgurAPI: ImgurAPI

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getImgurResponse() = imgurAPI.getCats()
}