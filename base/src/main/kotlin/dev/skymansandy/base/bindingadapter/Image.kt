package dev.skymansandy.base.bindingadapter

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dev.skymansandy.base.util.media.image.DrawableUtil
import kotlin.math.abs

object Image {

    private val colors = arrayOf(
        "#DB7093", "#CD5C5C", "#BA55D3", "#A52A2A",
        "#A0522D", "#9400D3", "#8B0000", "#6A5ACD",
        "#556B2F", "#191970", "#0000FF", "#228B22"
    )

    @JvmStatic
    @BindingAdapter("textDrawable")
    fun setTextDrawable(imageView: ImageView, text: String) {
        imageView.setImageDrawable(
            DrawableUtil.getTextDrawableRound(
                text, Color.parseColor(colors[abs(text.hashCode()) % colors.size])
            )
        )
    }
}