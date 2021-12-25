data class Player(private var position: Int) {
    private var _score = 0

    val score: Int
        get() = _score
    fun move(steps: Int): Int {
        require(steps > 0)
        position += steps
        while (position > 10) {
            position -= 10
        }
        _score += position
        return score
    }
}