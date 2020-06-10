package dev.skymansandy.base.util.general

import java.util.regex.Pattern

object TextUtil {

    fun matches(patternStr: String, contentStr: String): Boolean {
        val pattern = Pattern.compile(patternStr)
        val matcher = pattern.matcher(contentStr)
        return matcher.matches()
    }
}