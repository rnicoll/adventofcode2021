data class Value(val values: Set<Int>) {
    companion object {
        val ZERO = Value(setOf(0))
    }

    private fun calculate(other: Value, apply: (a: Int, b: Int) -> Int): Value {
        val outcomes: Set<Int> = this.values.flatMap { a ->
            other.values.map { b -> apply(a, b) }
        }.toSet()
        return Value(outcomes)
    }

    fun add(other: Value) = calculate(other) { a, b -> a + b }
    fun mul(other: Value) = calculate(other) { a, b -> a * b }
    fun div(other: Value) = calculate(other) { a, b -> a / b }
    fun mod(other: Value) = calculate(other) { a, b -> a % b }
    fun eql(other: Value) = calculate(other) { a, b ->
        if (a == b) {
            1
        } else {
            0
        }
    }

    override fun toString() = "$values"
}