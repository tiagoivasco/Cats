package com.ivasco.cats.model

class ImgurResponse(
    val success: Boolean = false,
    val status: Int = 0,
    val data: MutableList<PictureInfo> = ArrayList()
)