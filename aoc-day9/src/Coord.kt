data class Coord(val row: Int, val col: Int) {
    fun west(): Coord? {
        return if (col > 0) {
            Coord(row, col - 1)
        } else {
            null
        }
    }
    fun east(map: SeaMap): Coord? {
        return if (col < map.cols - 1) {
            Coord(row, col + 1)
        } else {
            null
        }
    }
    fun north(): Coord? {
        return if (row > 0) {
            Coord(row - 1, col)
        } else {
            null
        }
    }
    fun south(map: SeaMap): Coord? {
        return if (row < map.rows - 1) {
            Coord(row + 1, col)
        } else {
            null
        }
    }

    override fun toString() = "$row,$col"
}