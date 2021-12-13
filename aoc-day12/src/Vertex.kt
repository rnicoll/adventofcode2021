data class Vertex(val from: String, val to: String) {
    companion object {
        fun parse(input: String) = input.split("-").let {
            Vertex(it[0], it[1])
        }
    }

    override fun toString() = "$from-$to"
}