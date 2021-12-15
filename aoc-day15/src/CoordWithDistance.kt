data class CoordWithDistance(val coord: Coord, var distance: Int) : Comparable<CoordWithDistance> {
    override fun compareTo(other: CoordWithDistance): Int {
        return this.distance - other.distance
    }
    override fun equals(other: Any?) = other is CoordWithDistance && other.coord == this.coord
    override fun hashCode() = this.coord.hashCode()

    override fun toString() = "$coord: $distance"
}