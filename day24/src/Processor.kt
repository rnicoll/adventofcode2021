import java.util.*

class Processor {
    private val registers: EnumMap<Register, Value> = EnumMap(
        mapOf(
            Pair(Register.w, Value.ZERO),
            Pair(Register.x, Value.ZERO),
            Pair(Register.y, Value.ZERO),
            Pair(Register.z, Value.ZERO),
        )
    )

    fun getRegister(register: Register): Value? = registers[register]

    fun setRegister(register: Register, value: Value) {
        registers[register] = value
    }

    override fun toString() =
        "w=${registers[Register.w]}, x=${registers[Register.x]}, y=${registers[Register.y]}, z=${registers[Register.z]}"
}