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
    /**
     * @param row the middle row of the grid to calculate.
     * @param col the middle column of the grid to calculate.
     */
    internal fun getBits(row: Int, col: Int): List<Boolean> {
        val out = mutableListOf<Boolean>()
        for (rowIdx in row - 1..row + 1) {
            for (colIdx in col - 1..col + 1) {
                out.add(image.isOn(rowIdx, colIdx))
            }
        }
        return out
    }

    override fun isOn(row: Int, col: Int) = algorithm.pixels[calculate(getBits(row, col))]

    override val cols: Int
        get() = this.image.cols + (Image.EXPANSION * 2)

    override val rows: Int
        get() = this.image.rows + (Image.EXPANSION * 2)

    override fun toString(): String {
        val colBuilder = StringBuilder()
        for (row in (0 - Image.EXPANSION) until rows) {
            colBuilder.append(rowToString(row)).append("\n")
        }
        return colBuilder.toString()
    }

    private fun rowToString(row: Int): String {
        val builder = StringBuilder()
        for (col in (0 - Image.EXPANSION) until cols) {
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