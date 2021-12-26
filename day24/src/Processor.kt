data class Processor(var w: Int = 0, var x: Int = 0, var y: Int = 0, var z: Int = 0) {
    fun fork(instructionIdx: Int): ProcessorThread = ProcessorThread(this.copy(), instructionIdx)

    fun get(register: Register): Int = when(register) {
        Register.w -> {this.w}
        Register.x -> {this.x}
        Register.y -> {this.y}
        Register.z -> {this.z}
    }

    fun set(register: Register, value: Int) {
        when(register) {
            Register.w -> {this.w = value}
            Register.x -> {this.x = value}
            Register.y -> {this.y = value}
            Register.z -> {this.z = value}
        }
    }

    override fun toString() =
        "w=$w, x=$x, y=$y, z=$z"
}