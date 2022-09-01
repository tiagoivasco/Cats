package com.ivasco.cats.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ivasco.cats.R
import com.ivasco.cats.model.ImgurResponse
import com.ivasco.cats.view.adapter.PicturesAdapter
import com.ivasco.cats.viewmodel.CatsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var catsViewModel: CatsViewModel
    private val picturesAdapter = PicturesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catsViewModel = ViewModelProvider(this)[CatsViewModel::class.java]
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        recyclerView.adapter = picturesAdapter
        observeViewModel()
        catsViewModel.fetch()
    }

    private fun observeViewModel() {
        catsViewModel.apply {
            imgurResponse.observe(this@MainActivity) { observeData(it) }
            isError.observe(this@MainActivity) { observeError(it) }
            loading.observe(this@MainActivity) { observerLoadingDialog(it) }
        }
    }

    private fun observerLoadingDialog(loading: Boolean) {
        loading.let { progressBar.visibility = if (it) VISIBLE else GONE }

        if (loading) {
            recyclerView.visibility = GONE
            txtError.visibility = GONE
        }
    }

    private fun observeError(isError: Boolean) {
        txtError.visibility = if (isError) VISIBLE else GONE
    }

    private fun observeData(imgurResponse: ImgurResponse) {
        recyclerView.visibility = View.VISIBLE
        val picturesList = imgurResponse.data.flatMap { pictureInfo -> pictureInfo.images }
            .filter { images -> images.link.isNotEmpty() }
            .filter { images -> images.type == "image/jpeg" }
            .toMutableList()

        picturesList.forEach { image -> image.link = getMediumThumbnailURL(image.link) }
        picturesAdapter.updateData(picturesList)
    }

    private fun getMediumThumbnailURL(originalLink: String): String {
        var newString = ""
        val index = originalLink.length - 4

        for (i in originalLink.indices) {
            newString += originalLink[i]
            if (i == index - 1) newString += "m"
        }
        return newString
    }
}