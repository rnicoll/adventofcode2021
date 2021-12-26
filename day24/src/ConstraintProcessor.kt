class ConstraintProcessor(
    var w: Value = Value.ZERO,
    var x: Value = Value.ZERO,
    var y: Value = Value.ZERO,
    var z: Value = Value.ZERO,
    override val startIdx: Int
) : Processor<Value> {
    override fun get(register: Register) = when (register) {
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

    override fun set(register: Register, value: Value) {
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

    private fun calculate(to: Register, from: RegisterOrConstant, f: (a: Int, b: Int) -> Int): Value {
        return Value(to.readConstraint(this).values.flatMap { a ->
            from.readConstraint(this).values.map { b ->
                f(a, b)
            }
        }.toSet())
    }

    /**
     * Determine if this processor _could_ end with a zero in the z register, at all.
     */
    fun process(instructions: List<Instruction>): Boolean {
        val instructionIdx = this.startIdx
        for (i in instructionIdx until instructions.size) {
            this.process(instructions[i])
        }
        return 0 in this.z.values
    }

    private fun process(ins: Instruction) {
        when (ins) {
            is Input -> {
                this.set(ins.to, Value.ONE_TO_NINE)
            }
            is Add -> {
                this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a + b})
            }
            is Mul -> {
                this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a * b})
            }
            is Div -> {
                this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a / b})
            }
            is Mod -> {
                this.set(ins.to, calculate(ins.to, ins.from) { a, b -> a % b})
            }
            is Eql -> {
                this.set(ins.to, calculate(ins.to, ins.from) { a, b ->
                    if (a == b) {
                        1
                    } else {
                        0
                    }
                })
            }
        }
    }
}

