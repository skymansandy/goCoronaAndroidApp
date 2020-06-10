package dev.skymansandy.base.dsl

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StringDslTest {

    @Test
    fun test_StringBuilder() {
        assertEquals("ThisIsCrazy", buildString {
            append("This")
            append("Is")
            append("Crazy")
        })
    }
}