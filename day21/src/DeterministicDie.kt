class DeterministicDie: Dice {
    private var count = 0
    override fun roll() = ++count
    override val rollCount: Int
        get() = count
}