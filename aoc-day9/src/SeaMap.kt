class SeaMap(private val map: Array<IntArray>) {
    fun get(coord: Coord): Int {
        return map[coord.row][coord.col]
    }

    val cols
        get() = map[0].size
    val rows
        get() = map.size
}