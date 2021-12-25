object Day21  {
    const val TARGET_SCORE = 1000
    const val INPUT_SHORT = "Player 1 starting position: 6\nPlayer 2 starting position: 8"
}

fun main() {
    val die = DeterministicDie()
    val player1 = Player(6)
    val player2 = Player(8)
    while (true) {
        if (player1.move(die.roll() + die.roll() + die.roll()) >= Day21.TARGET_SCORE) {
            win(player1, player2, die)
            break
        } else if (player2.move(die.roll() + die.roll() + die.roll()) >= Day21.TARGET_SCORE) {
            win(player2, player2, die)
            break
        }
    }
}

fun win(winner: Player, loser: Player, die: Dice) {
    println("Loser score: " + loser.score)
    println("Winner score: " + winner.score)
    println("Die rolls: " + die.rollCount)
    println(loser.score * die.rollCount)
}