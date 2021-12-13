import java.util.*

data class Scenario(val inputs: List<EnumSet<Signal>>, val outputs: List<EnumSet<Signal>>) {
    companion object {
        fun parse(input: String) : Scenario {
            val parts = input.trim().split("|")
            require(parts.size == 2)
            val inputs = parseSegments(parts[0].trim())
            val outputs = parseSegments(parts[1].trim())
            return Scenario(inputs, outputs)
        }

        private fun parseSegments(input: String): List<EnumSet<Signal>>
            = input.split(" ").map {
                val segments = EnumSet.noneOf(Signal::class.java)
                val chars = it.toCharArray()
                for (i in chars.indices) {
                    segments.add(Signal.valueOf(chars[i].toString()))
                }
                segments
        }
    }

    override fun toString() = inputs.joinToString(" ") { input -> input.joinToString("") { it.name } }
}