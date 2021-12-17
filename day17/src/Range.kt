data class Range(val min: Int, val max: Int) {
    companion object {
        fun parse(input: String): Range {
            val joinIdx = input.indexOf("..")
            val min = input.substring(0 until joinIdx).toInt()
            val max = input.substring(joinIdx + 2, input.length).toInt()
            return Range(min, max)
        }
    }

    override fun toString() = "$min..$max"
}