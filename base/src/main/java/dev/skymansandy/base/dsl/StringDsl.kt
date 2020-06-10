package dev.skymansandy.base.dsl

fun buildString(action: (StringBuilder).() -> Unit): String {
    val stringBuilder = StringBuilder()
    action(stringBuilder)
    return stringBuilder.toString()
}