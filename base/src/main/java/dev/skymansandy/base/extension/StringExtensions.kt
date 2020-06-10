package dev.skymansandy.base.extension

fun StringBuilder.appendSpace(str: String) {
    this.append("$str ")
}

fun StringBuilder.spaceAppend(str: String) {
    this.append(" $str")
}