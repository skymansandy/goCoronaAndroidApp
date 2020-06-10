package dev.skymansandy.base.util.media.image

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.amulyakhare.textdrawable.TextDrawable


object DrawableUtil {

    private fun getInitialsString(name: String?): String {
        return name?.takeIf { it.split(" ").isNotEmpty() }.let {
            val initials = StringBuilder()
            var count = 0
            for (s in it!!.split(" ")) {
                if (count == 2) break
                if (s.isNotEmpty()) {
                    initials.append(s[0])
                    count++
                }
            }
            initials.toString()
        }
    }

    fun getTextDrawableRound(name: String, color: Int): TextDrawable {
        return TextDrawable.builder()
            .beginConfig()
            .withBorder(0)
            .endConfig()
            .round()
            .build(getInitialsString(name), color)
    }

    fun getGradient(): GradientDrawable {
        return GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.RED, Color.GREEN)
        ).apply {
            cornerRadius = 0f
        }
    }

}