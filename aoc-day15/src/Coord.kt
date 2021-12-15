open class Coord(val col: Int, val row: Int) {
    /**
     * Get a list of all valid co-ordinates to proceed to, from this coordinate on the map
     */
    fun adjacent(map: CaveMap): List<Coord> {
        val coords = mutableListOf<Coord>()
        if (col > 0) {
            coords.add(Coord(col - 1, row))
        }
        if (col < map.cols - 1) {
            coords.add(Coord(col + 1, row))
        }
        if (row > 0) {
            coords.add(Coord(col, row - 1))
        }
        if (row < map.rows - 1) {
            coords.add(Coord(col, row + 1))
        }
        return coords
    }

    override fun equals(other: Any?) = other is Coord && other.col == this.col && other.row == this.row
    override fun hashCode(): Int {
        var hash = 1
        hash = hash * 31 + row
        hash = hash * 31 + col
        return hash
    }

    override fun toString() = "$col,$row"
}