import java.util.*

class Algorithm(val pixels: BooleanArray) {
    companion object {
        fun parse(iterator: Spliterator<String>): Algorithm {
            var line = ""
            iterator.tryAdvance { line = it }
            return Algorithm(Image.parseRow(line))
        }
    }

    override fun toString() = pixels.joinToString("") {
        if (it) {
            "#"
        } else {
            "."
        }
    }
}