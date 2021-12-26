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

    fun read(processor: Processor): Int
}

enum class Register : RegisterOrConstant {
    w,
    x,
    y,
    z;

    override fun read(processor: Processor) = processor.get(this)
}

class Constant(private val value: Int): RegisterOrConstant {
    override fun toString() = value.toString()
    override fun read(processor: Processor) = value
}