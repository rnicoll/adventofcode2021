data class Target(val xRange: Range, val yRange: Range) {
    companion object {
        fun parse(input: String): Target {

            val parts = input.split(",").map { it.trim() }
            val xRange = parts[0].split("=")[1]
            val yRange = parts[1].split("=")[1]
            return Target(Range.parse(xRange), Range.parse(yRange))
        }
    }

    override fun toString() = "x=$xRange, y=$yRange"
}