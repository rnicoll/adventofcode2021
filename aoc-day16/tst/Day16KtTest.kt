import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    @Test
    fun toInt5() {
        val input = "101".toCharArray().map { it.digitToInt().toByte() }
        val expected = 5
        val actual = input.toInt()
        assertEquals(expected, actual)
    }

    @Test
    fun toInt2021() {
        val input = "011111100101".toCharArray().map { it.digitToInt().toByte() }
        val expected = 2021
        val actual = input.toInt()
        assertEquals(expected, actual)
    }
}