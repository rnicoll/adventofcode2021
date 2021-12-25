import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EnhanceTest {
    companion object {
        const val ALGORITHM =
            "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"
        const val MAP = "#..#.\n" +
                "#....\n" +
                "##..#\n" +
                "..#..\n" +
                "..###"
    }

    @Test
    fun calculate() {
        val bits = CalculateBitsTestcase.bitsToList("000100010")
        val expected = 34
        val actual = Enhance.calculate(bits)
        assertEquals(expected, actual)
    }

    @Test
    fun getBits() {
        val algorithm = Algorithm.parse(ALGORITHM)
        val rawImage = RawImage.parse(MAP.split("\n").spliterator())

        arrayOf(
            CalculateBitsTestcase("000000001", -1, -1),
            CalculateBitsTestcase("000010010", 0, 0),
            CalculateBitsTestcase("100100110", 1, 1),
            CalculateBitsTestcase("000100010", 2, 2)
        ).forEach { tc ->
            val actual = Enhance(rawImage, algorithm).getBits(tc.row, tc.col)
            assertEquals(tc.expectedBits, actual)
        }
    }

    @Test
    fun isOn() {
        val algorithm = Algorithm.parse(ALGORITHM)
        val rawImage = RawImage.parse(MAP.split("\n").spliterator())
        val enhance = Enhance(rawImage, algorithm)
        assertEquals(false, rawImage.isOn(2, 2))
        val expected = true
        val actual = enhance.isOn(2, 2)
        assertEquals(expected, actual)
    }
}

data class CalculateBitsTestcase(val expected: String, val row: Int, val col: Int) {
    companion object {
        fun bitsToList(input: String) = input.toCharArray().map { it == '1' }.toList()
    }
    val expectedBits: List<Boolean>
        = bitsToList(expected)
}