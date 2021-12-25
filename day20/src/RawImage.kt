import java.util.*

class RawImage(private val pixels: Array<BooleanArray>): Image {
    companion object {
        fun parse(iterator: Spliterator<String>): RawImage {
            val rows = mutableListOf<BooleanArray>()
            iterator.forEachRemaining { line ->
                rows.add(parseRow(line))
            }
            return RawImage(rows.toTypedArray())
        }
        fun parseRow(line: String) = line.toCharArray().map { c -> c == '#' }.toBooleanArray()
    }

    override val cols: Int
        get() = pixels.size

    override val rows: Int
        get() = pixels[0].size

    override fun isOn(row: Int, col: Int): Boolean {
        if (row < 0 || col < 0) {
            return false
        }
        if (row >= pixels.size || col >= pixels[0].size) {
            return false
        }
        return pixels[row][col]
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