package com.englesoft.randomphotogallery.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import com.englesoft.randomphotogallery.databinding.LayoutImageItemBinding
import com.englesoft.randomphotogallery.utils.loadImageFromUrl

class ImageListAdapter(private val imageItemClickListener: ImageItemClickListener) :
    RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    private var imageList: List<ImageListResponse> = listOf()

    inner class ImageViewHolder(val binding: LayoutImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        with(holder.binding) {
            with(imageList[position].image) {
                ivThumbnail.loadImageFromUrl(holder.itemView.context, thumb)
            }
        }

        holder.itemView.setOnClickListener {
            imageItemClickListener.onClick(imageList[position])
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ImageListResponse>) {
        imageList = list
        notifyDataSetChanged()
    }

    interface ImageItemClickListener {
        fun onClick(imageListResponse: ImageListResponse)
    }
}