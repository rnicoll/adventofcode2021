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
}

enum class Register : RegisterOrConstant {
    w,
    x,
    y,
    z
}

class Constant(val value: Int): RegisterOrConstant {
    override fun toString() = value.toString()
}