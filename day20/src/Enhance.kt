import java.lang.StringBuilder

class Enhance(private val image: Image, private val algorithm: Algorithm) : Image {
    companion object {
        fun calculate(bits: Iterable<Boolean>): Int {
            var out = 0
            for (bit in bits) {
                out = out.shl(1)
                if (bit) {
                    out += 1
                }
            }
            return out
        }
    }
    private val cache = mutableMapOf<Pair<Int, Int>, Boolean>()

    /**
     * @param row the middle row of the grid to calculate.
     * @param col the middle column of the grid to calculate.
     */
    internal fun getBits(row: Int, col: Int): List<Boolean> {
        val out = mutableListOf<Boolean>()
        for (rowIdx in row - 1..row + 1) {
            for (colIdx in col - 1..col + 1) {
                out.add(image.isOn(rowIdx - Image.EXPANSION, colIdx - Image.EXPANSION))
            }
        }
        return out
    }

    override fun isOn(row: Int, col: Int) = cache.computeIfAbsent(Pair(row, col)) { key ->
        algorithm.pixels[calculate(getBits(key.first, key.second))]
    }

    override val cols: Int
        get() = this.image.cols + (Image.EXPANSION * 2)

    override val rows: Int
        get() = this.image.rows + (Image.EXPANSION * 2)

    override fun toString(): String {
        val colBuilder = StringBuilder()
        for (row in 0 until rows) {
            colBuilder.append(rowToString(row)).append("\n")
        }
        return colBuilder.toString()
    }

    private fun rowToString(row: Int): String {
        val builder = StringBuilder()
        for (col in 0 until cols) {
            builder.append(
                if (isOn(row, col)) {
                    "#"
                } else {
                    "."
                }
            )
        }
        return builder.toString()
    }
}