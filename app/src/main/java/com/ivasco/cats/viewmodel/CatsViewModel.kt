package com.ivasco.cats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivasco.cats.di.DaggerApiComponent
import com.ivasco.cats.model.ImgurResponse
import com.ivasco.cats.service.ImgurService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CatsViewModel : ViewModel() {

    val imgurResponse = MutableLiveData<ImgurResponse>()
    val isError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var imgurService: ImgurService

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun fetch() {
        loading.value = true
        disposable.add(
            imgurService.getImgurResponse()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ImgurResponse>() {
                    override fun onSuccess(value: ImgurResponse?) {
                        imgurResponse.value = value
                        sucessResult()
                    }

                    override fun onError(e: Throwable?) {
                        errorResult()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun sucessResult() {
        isError.value = false
        loading.value = false
    }

    private fun errorResult() {
        isError.value = true
        loading.value = false
    }
}