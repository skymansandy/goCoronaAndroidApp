package dev.skymansandy.base.util.general

import android.content.Context
import android.text.Spannable
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import java.util.regex.Pattern

object TextUtil {

    fun matches(patternStr: String, contentStr: String): Boolean {
        val pattern = Pattern.compile(patternStr)
        val matcher = pattern.matcher(contentStr)
        return matcher.matches()
    }


    fun getUrlClickableSpan(context: Context?, url: String): Spannable? {
        return context?.let {
            val span = Spannable.Factory.getInstance().newSpannable(url)
            span.setSpan(object : ClickableSpan() {
                override fun onClick(v: View) {
                    AppUtil.launchUrl(context, url)
                }
            }, 0, url.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            span
        }
    }
}