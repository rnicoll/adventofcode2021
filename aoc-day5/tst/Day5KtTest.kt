import org.junit.jupiter.api.Assertions.*

internal class Day5KtTest {

    @org.junit.jupiter.api.Test
    fun getCoords2HorizontalLine() {
        val line = Line(Coord(0, 9), Coord(5, 9))
        val expected = getCoords(line.from, line.to).toList()
        val actual = getCoords2(line.from, line.to).toList()
        assertEquals(expected, actual)
    }

    @org.junit.jupiter.api.Test
    fun getCoords2DiagonalLine() {
        val line = Line(Coord(6, 4), Coord(2, 0))
        val expected = listOf(
            Coord(2, 0),
            Coord(3, 1),
            Coord(4, 2),
            Coord(5, 3),
            Coord(6, 4),
        )
        val actual = getCoords2(line.from, line.to).toList()
        assertEquals(expected, actual)
    }

    @org.junit.jupiter.api.Test
    fun getCoords2DiagonalLine2() {
        val line = Line(Coord(5, 5), Coord(8, 2))
        val expected = listOf(
            Coord(5, 5),
            Coord(6, 4),
            Coord(7, 3),
            Coord(8, 2)
        )
        val actual = getCoords2(line.from, line.to).toList()
        assertEquals(expected, actual)
    }

    @org.junit.jupiter.api.Test
    fun getCoords2VerticalLine() {
        val line = Line(Coord(9, 4), Coord(3, 4))
        val expected = getCoords(line.from, line.to).toList().sorted()
        val actual = getCoords2(line.from, line.to).toList().sorted()
        assertEquals(expected, actual)
    }
}