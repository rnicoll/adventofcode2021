interface Image {
    companion object {
        const val EXPANSION = 2
    }
    val cols: Int
    val rows: Int
    fun isOn(row: Int, col: Int): Boolean
}