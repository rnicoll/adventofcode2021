data class Processor(var w: Int = 0, var x: Int = 0, var y: Int = 0, var z: Int = 0, var instructionIdx: Int = 0) {
    fun fork(newIdx: Int): Processor = this.copy(instructionIdx = newIdx)

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

    fun process(instructions: List<Instruction>): Pair<Boolean, List<Int>> {
        val startIdx = this.instructionIdx
        for (i in startIdx until instructions.size) {
            when (val ins = instructions[i]) {
                is Input -> {
                    // Fork 9 ways
                    for (fork in 9 downTo 1) {
                        val subprocessor = this.fork(i + 1)
                        subprocessor.set(ins.to, fork)
                        val outcome = if (cache.containsKey(subprocessor)) {
                            cache[subprocessor]!!
                        } else {
                            subprocessor.process(instructions).also {
                                cache[subprocessor] = it
                            }
                        }
                        if (outcome.first) {
                            return Pair(true, listOf(fork) + outcome.second)
                        }
                    }
                    // Don't try this path again
                    return Pair(false, emptyList())
                }
                is Add -> {
                    this.set(ins.to, ins.to.read(this) + ins.from.read(this))
                }
                is Mul -> {
                    this.set(ins.to, ins.to.read(this) * ins.from.read(this))
                }
                is Div -> {
                    this.set(ins.to, ins.to.read(this) / ins.from.read(this))
                }
                is Mod -> {
                    this.set(ins.to, ins.to.read(this) % ins.from.read(this))
                }
                is Eql -> {
                    this.set(
                        ins.to, if (ins.to.read(this) == ins.from.read(this)) {
                            1
                        } else {
                            0
                        }
                    )
                }
            }
        }
        return if (this.get(Register.z) == 0) {
            Pair(true, emptyList())
        } else {
            Pair(false, emptyList())
        }
    }

    override fun toString() =
        "w=$w, x=$x, y=$y, z=$z at $instructionIdx"
}