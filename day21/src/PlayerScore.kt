class PlayerScore(val player: Player, private var position: Int, private var _score:  Int = 0) {
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
    fun split(steps: Int): PlayerScore {
        require(steps > 0)
        var newPosition = position + steps
        while (newPosition > 10) {
            newPosition -= 10
        }
        return PlayerScore(this.player, newPosition, _score + newPosition)
    }

    override fun equals(other: Any?) = other is PlayerScore && this.player == other.player
    override fun hashCode() = this.player.hashCode()
    override fun toString() = this.player.name
}