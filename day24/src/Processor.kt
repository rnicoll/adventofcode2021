import java.util.*

class Processor() {
    private val registers: EnumMap<Register, Value> = EnumMap(mapOf(
        Pair(Register.w, Value.ZERO),
        Pair(Register.x, Value.ZERO),
        Pair(Register.y, Value.ZERO),
        Pair(Register.z, Value.ZERO),
    ))
}