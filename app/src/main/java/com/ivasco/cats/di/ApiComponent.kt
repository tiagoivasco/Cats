package com.ivasco.cats.di

import com.ivasco.cats.service.ImgurService
import com.ivasco.cats.viewmodel.CatsViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service : ImgurService)
    fun inject(catsViewModel: CatsViewModel)
}