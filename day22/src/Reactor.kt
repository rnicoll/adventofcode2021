class Reactor(val size: Int) {
    val grid: Array<Array<BooleanArray>> = Array(size * 2 + 1) { Array(size * 2 + 1) { BooleanArray(size * 2 + 1) { false } } }

    fun apply(x: IntRange, y: IntRange, z: IntRange, on: Boolean) {
        if (x.first < -size || x.endInclusive > size
            || y.first < -size || y.endInclusive > size
            || z.first < -size || z.endInclusive > size) {
            return
        }
        for (xCoord in x) {
            for (yCoord in y) {
                for (zCoord in z) {
                    grid[xCoord + size][yCoord + size][zCoord + size] = on
                }
            }
        }
    }

    fun countOn(): Int {
        var count = 0
        for (xCoord in grid.indices) {
            for (yCoord in grid[0].indices) {
                for (zCoord in grid[0][0].indices) {
                    if (grid[xCoord][yCoord][zCoord]) {
                        count++
                    }
                }
            }
        }
        return count
    }
}