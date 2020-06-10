package dev.skymansandy.base.extension

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StringDslTest {

    @Test
    fun test_StringAppends() {
        val builder = StringBuilder()

        builder.appendSpace("Appended")
        assertEquals("Appended ", builder.toString())

        builder.clear()
        builder.spaceAppend("Appended")
        assertEquals(" Appended", builder.toString())
    }
}