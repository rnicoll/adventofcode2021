import java.util.*

class Image(val pixels: Array<BooleanArray>) {
    companion object {
        private const val EXPANSION = 2

        fun parse(iterator: Spliterator<String>): Image {
            val rows = mutableListOf<BooleanArray>()
            iterator.forEachRemaining { line ->
                rows.add(parseRow(line))
            }
            return Image(rows.toTypedArray())
        }
        fun parseRow(line: String) = line.toCharArray().map { c -> c == '#' }.toBooleanArray()
    }

    private fun calculateBits(row: Int, col: Int): Int {
        var out = 0
        for (i in 0..2) {
            for (j in 0..2) {
                out = out.shl(1)
                if (isOn(row - EXPANSION + i, col - EXPANSION + j)) {
                    out += 1
                }
            }
        }
        return out
    }

    private fun isOn(row: Int, col: Int): Boolean {
        if (row < 0 || col < 0) {
            return false
        }
        if (row >= pixels.size || col >= pixels[0].size) {
            return false
        }
        return pixels[row][col]
    }

    fun enhance(algorithm: Algorithm): Image {
        val enhanced = emptyExpand(EXPANSION)
        for (row in enhanced.indices) {
            for (col in enhanced[0].indices) {
                enhanced[row][col] = algorithm.pixels[calculateBits(row, col)]
            }
        }
        return Image(enhanced)
    }

    private fun emptyExpand(cells: Int): Array<BooleanArray> = Array(pixels.size + (cells * 2)) { _ ->
        BooleanArray(pixels[0].size + (cells * 2)) { false }
    }

    override fun toString() = pixels.joinToString("\n") { row ->
        row.joinToString("") { pixel ->
            if (pixel) {
                "#"
            } else {
                "."
            }
        }
    }
}