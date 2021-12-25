enum class LeftRight() {
    LEFT,
    RIGHT;

    fun opposite(): LeftRight {
        return when (this) {
            LEFT -> {RIGHT}
            RIGHT -> {LEFT}
        }
    }
}