import java.util.*

enum class Digit(val num: Int, val active: EnumSet<Position>) {
    ZERO(0, EnumSet.of(Position.TOP, Position.BOTTOM, Position.UPPER_LEFT,
        Position.UPPER_RIGHT, Position.LOWER_LEFT, Position.LOWER_RIGHT)),
    ONE(1, EnumSet.of(Position.UPPER_RIGHT, Position.LOWER_RIGHT)),
    TWO(2, EnumSet.of(Position.TOP, Position.BOTTOM, Position.MIDDLE,
        Position.UPPER_RIGHT, Position.LOWER_LEFT)),
    THREE(3, EnumSet.of(Position.TOP, Position.BOTTOM, Position.MIDDLE,
        Position.UPPER_LEFT, Position.LOWER_LEFT)),
    FOUR(4, EnumSet.of(Position.UPPER_RIGHT, Position.LOWER_RIGHT, Position.MIDDLE, Position.UPPER_LEFT)),
    FIVE(5, EnumSet.of(Position.TOP, Position.BOTTOM, Position.MIDDLE,
        Position.UPPER_LEFT, Position.LOWER_RIGHT)),
    SIX(6, EnumSet.of(Position.TOP, Position.BOTTOM, Position.MIDDLE,
        Position.UPPER_LEFT, Position.LOWER_LEFT, Position.LOWER_RIGHT)),
    SEVEN(7, EnumSet.of(Position.UPPER_RIGHT, Position.LOWER_RIGHT, Position.TOP)),
    EIGHT(8, EnumSet.allOf(Position::class.java)),
    NINE(9, EnumSet.of(Position.TOP, Position.BOTTOM, Position.MIDDLE,
        Position.UPPER_LEFT, Position.UPPER_RIGHT, Position.LOWER_RIGHT)),
}