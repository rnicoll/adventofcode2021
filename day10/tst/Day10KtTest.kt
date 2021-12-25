import org.junit.jupiter.api.Assertions.*

internal class Day10KtTest {

    @org.junit.jupiter.api.Test
    fun completeLine() {
        val input = "[({(<(())[]>[[{[]{<()<>>"
        val expected = "}}]])})]".toCharArray().toList()
        val actual = completeLine(input).toList()
        assertEquals(expected, actual)
    }
}