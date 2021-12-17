data class TargetArea(val xRange: Range, val yRange: Range) {
    companion object {
        fun parse(input: String): TargetArea {

            val parts = input.split(",").map { it.trim() }
            val xRange = parts[0].split("=")[1]
            val yRange = parts[1].split("=")[1]
            return TargetArea(Range.parse(xRange), Range.parse(yRange))
        }
    }

    override fun toString() = "x=$xRange, y=$yRange"
}