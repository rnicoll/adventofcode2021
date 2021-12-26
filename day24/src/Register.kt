sealed interface RegisterOrConstant {
    companion object {
        fun parse(input: String): RegisterOrConstant {
            return if (input[0].isLetter()) {
                Register.valueOf(input)
            } else {
                Constant(input.toInt())
            }
        }
    }

    fun readInt(processor: Processor<Int>): Int
    fun readConstraint(processor: Processor<Value>): Value
}

enum class Register : RegisterOrConstant {
    w,
    x,
    y,
    z;

    override fun readInt(processor: Processor<Int>) = processor.get(this)
    override fun readConstraint(processor: Processor<Value>) = processor.get(this)
}

class Constant(private val value: Int): RegisterOrConstant {
    override fun toString() = value.toString()
    override fun readInt(processor: Processor<Int>) = value
    override fun readConstraint(processor: Processor<Value>) = Value(setOf(this.value))
}