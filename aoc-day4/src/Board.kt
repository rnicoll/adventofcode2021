import kotlin.collections.ArrayList

class Board(private val rows: Array<Row>) {
    companion object {
        fun read(input: Iterator<String>): Board {
            val rows = ArrayList<Row>(Day4.BOARD_SIZE)
            for (i in 0 until Day4.BOARD_SIZE) {
                rows.add(Row.parse(input.next()))
            }
            return Board(rows.toTypedArray())
        }
    }

    fun match(num: Int) = this.rows.forEach { it.match(num) }
    fun isMatched(): Boolean {
        val matchedRows = rows.filter { it.isRowMatched() }
        if (matchedRows.isNotEmpty()) return true
        for (col in 0 until Day4.BOARD_SIZE) {
            var matchedCol = true
            for (row in 0 until Day4.BOARD_SIZE) {
                matchedCol = matchedCol && rows[row].isMatched(col)
            }
            if (matchedCol) {
                return true
            }
        }
        return false
    }
    fun score() = rows.sumOf { it.score() }
    override fun toString() = rows.contentToString()
}

class Row(private val numbers: List<Int>) {
    companion object {
        fun parse(input: String): Row {
            val numbers = ArrayList<Int>(Day4.BOARD_SIZE)
            for (i in 0 until Day4.BOARD_SIZE) {
                val start = 3 * i
                val t = input.substring(start, start + 2).trim()
                numbers.add(Day4.NUMBER_PARSER.parse(t).toInt())
            }
            return Row(numbers)
        }
    }
    private val matched: MutableList<Boolean> = numbers.map { false }.toMutableList()

    fun get(i: Int) = numbers[i]
    fun match(num: Int) {
        for (i in numbers.indices) {
            if (numbers[i] == num) {
                matched[i] = true
            }
        }
    }
    fun isRowMatched() = matched.all { it }
    fun isMatched(col: Int) = matched[col]
    fun score() = numbers.mapIndexed { index, i ->
        if (!matched[index]) {
            i
        } else {
            0
        }
    }.sum()
    override fun toString(): String {
        return numbers.toTypedArray().contentToString()
    }
}