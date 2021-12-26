class Value(val values: Set<Int>) {
    companion object {
        val ZERO = Value(setOf(0))
        val ONE_TO_NINE = Value(setOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        fun of(i: Int) = Value(setOf(i))
    }
}