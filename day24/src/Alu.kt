data class Alu(
    var w: Int = 0,
    var x: Int = 0,
    var y: Int = 0,
    var z: Int = 0,
    override val startIdx: Int = 0
) : Processor<Int> {
    private fun fork(newIdx: Int): Alu = this.copy(startIdx = newIdx)
    private fun constraint(start: Int): ConstraintProcessor = ConstraintProcessor(
        w = Value.of(w),
        x = Value.of(x),
        y = Value.of(y),
        z = Value.of(z),
        startIdx = start
    )

    override fun get(register: Register): Int = when (register) {
        Register.w -> {
            this.w
        }
        Register.x -> {
            this.x
        }
        Register.y -> {
            this.y
        }
        Register.z -> {
            this.z
        }
    }

    override fun set(register: Register, value: Int) {
        when (register) {
            Register.w -> {
                this.w = value
            }
            Register.x -> {
                this.x = value
            }
            Register.y -> {
                this.y = value
            }
            Register.z -> {
                this.z = value
            }
        }
    }

    private fun calculate(to: Register, from: RegisterOrConstant, f: (a: Int, b: Int) -> Int) =
        f(to.readInt(this), from.readInt(this))

    fun process(instructions: List<Instruction>): Pair<Boolean, List<Int>> {
        for (i in this.startIdx until instructions.size) {
            when (val ins = instructions[i]) {
                is Input -> {
                    // Before evaluating every option under this, see if any of them could succeed
                    println("Simulating constraints from $i ($ins): $this")
                    val constraintProcessor = this.constraint(i)
                    if (!constraintProcessor.process(instructions)) {
                        return Pair(false, emptyList())
                    }

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
                    this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a + b })
                }
                is Mul -> {
                    this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a * b })
                }
                is Div -> {
                    this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a / b })
                }
                is Mod -> {
                    this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a % b })
                }
                is Eql -> {
                    this.set(
                        ins.to, calculate(ins.to, ins.from) { a, b ->
                            if (a == b) {
                                1
                            } else {
                                0
                            }
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
        "w=$w, x=$x, y=$y, z=$z at $startIdx"
}