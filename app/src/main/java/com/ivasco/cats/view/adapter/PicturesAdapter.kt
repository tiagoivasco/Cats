package com.ivasco.cats.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.ivasco.cats.R
import com.ivasco.cats.model.Images
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_pictures.view.*

class PicturesAdapter(
    context: Context,
    private val pictureInfoList: MutableList<Images> = ArrayList()
) : AbstractAdapter<Images>(context, pictureInfoList) {

    fun updateData(serverPictures: MutableList<Images>) {
        pictureInfoList.addAll(serverPictures)
        notifyDataSetChanged()
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): VHPictures {
        return VHPictures(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_pictures,
                parent,
                false
            )
        )
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, genericType: Images) {
        holder.setIsRecyclable(false)
        if (holder is VHPictures) holder.bind(context, genericType)
    }

    class VHPictures(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPicture: AppCompatImageView = view.img

        fun bind(context: Context, image: Images) {
            Picasso.with(context).load(image.link)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(imgPicture)

            itemView.setOnClickListener {
                image.description?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}