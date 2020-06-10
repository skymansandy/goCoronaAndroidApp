package dev.skymansandy.base.dsl

import android.text.Spannable
import android.text.SpannableString
import android.text.style.*
import androidx.annotation.ColorInt

infix fun SpannableString.concat(s: SpannableString) = SpannableString("$this$s")

fun spannable(func: () -> SpannableString) = func()

private fun span(s: CharSequence, o: Any) =
    if (s is String) SpannableString(s)
    else (s as? SpannableString ?: SpannableString("")).apply {
        setSpan(o, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

operator fun SpannableString.plus(s: SpannableString) =
    SpannableString(this concat s)

operator fun SpannableString.plus(s: String) =
    SpannableString("$this$s")

fun bold(s: CharSequence) =
    span(s, StyleSpan(android.graphics.Typeface.BOLD))

fun italic(s: CharSequence) =
    span(s, StyleSpan(android.graphics.Typeface.ITALIC))

fun boldItalic(s: CharSequence) =
    span(s, StyleSpan(android.graphics.Typeface.BOLD_ITALIC))

fun sub(s: CharSequence) =
    span(s, SubscriptSpan())

fun size(size: Float, s: CharSequence) =
    span(s, RelativeSizeSpan(size))

fun color(@ColorInt color: Int, s: CharSequence) =
    span(s, ForegroundColorSpan(color))

fun url(url: String, s: CharSequence) =
    span(s, URLSpan(url))