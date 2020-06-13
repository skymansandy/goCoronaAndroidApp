package dev.skymansandy.gocorona.presentation.health.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.skymansandy.gocorona.databinding.ItemImageBinding
import javax.inject.Inject

class ImageItemAdapter(
    private val imageClickListener: ImageClickListener
) : ListAdapter<ImageItem, ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(
            ItemImageBinding.inflate(inflater, parent, false),
            imageClickListener
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * Image click listener
 */
interface ImageClickListener {
    fun onImageClick(imageItem: ImageItem)
}

/**
 * Image ViewHolder
 */
class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val imageClickListener: ImageClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            binding.item?.let { item ->
                imageClickListener.onImageClick(item)
            }
        }
    }

    fun bind(item: ImageItem) {
        binding.item = item
    }
}

/**
 * Image Diff Callback
 */
class ImageDiffCallback @Inject constructor() : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem) = oldItem == newItem
}

data class ImageItem(
    val url: String = "",
    val altText: String = ""
)