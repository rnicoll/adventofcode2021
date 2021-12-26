import java.lang.RuntimeException

data class Value(val min: Int, val max: Int) {
    companion object {
        val ZERO = Value(0, 0)
    }

    val indices: Iterable<Int>
        get() = IntRange(min, max)

    fun add(other: Value) = Value(min + other.min, max + other.max)
    fun mul(other: Value) = Value(min * other.min, max * other.max)
    fun div(other: Value): Value {
        val outcomes: Set<Int> = this.indices.flatMap { a ->
            other.indices.map { b ->
                a / b
            }
        }.toSet()
        return Value(outcomes.minOrNull()!!, outcomes.maxOrNull()!!)
    }

    fun mod(other: Value): Value {
        val outcomes: Set<Int> = this.indices.flatMap { a ->
            other.indices.map { b ->
                a % b
            }
        }.toSet()
        return Value(outcomes.minOrNull()!!, outcomes.maxOrNull()!!)
    }

    fun eql(other: Value): Value {
        val outcomes: Set<Int> = this.indices.flatMap { a ->
            other.indices.map { b ->
                if (a == b) {
                    1
                } else {
                    0
                }
            }
        }.toSet()
        return Value(outcomes.minOrNull()!!, outcomes.maxOrNull()!!)
    }

    override fun toString() = "$min..$max"
}