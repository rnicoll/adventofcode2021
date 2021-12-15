data class Instruction(val input: String, val insert: Char) {
    companion object {
        fun parse(input: String): Instruction {
            val separator = input.indexOf(" -> ")
            return Instruction(input.substring(0, separator), input[separator + 4])
        }
    }

    override fun toString() = "$input -> $insert"
}
