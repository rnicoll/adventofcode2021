data class Grid(val dots: Array<BooleanArray>) {
    companion object {
        fun parse(input: List<String>): Grid {
            val coords = input.map { line ->
                line.split(",").map { it.toInt() }.let {
                    Pair(it[0], it[1])
                }
            }
            val width = coords.maxOf { it.first } + 1
            val height = coords.maxOf { it.second } + 1
            val grid = Array(height) { BooleanArray(width) {false} }
            coords.forEach {
                grid[it.second][it.first] = true
            }
            return Grid(grid)
        }
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grid

        if (!dots.contentDeepEquals(other.dots)) return false

        return true
    }

    override fun hashCode(): Int {
        return dots.contentDeepHashCode()
    }

    override fun toString() = dots.joinToString("\n") { row ->
        row.joinToString("") {
            if (it) {
                "#"
            } else {
                "."
            }
        }
    }
}