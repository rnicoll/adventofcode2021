class CaveMap(val costs: Array<IntArray>) {
    fun get(coord: Coord) = costs[coord.row][coord.col]
    val cols
        get() = costs[0].size
    val rows
        get() = costs.size
}