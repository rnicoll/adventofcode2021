sealed interface Instruction {
    fun apply(processor: Processor)
}

// Read an input value and write it to variable a.
class Input(val to: Register) : Instruction {
    override fun toString() = "inp $to"
    override fun apply(processor: Processor) {
        processor.setRegister(to, Value(1, 9))
    }
}

// Add the value of a to the value of b, then store the result in variable a.
class Add(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "add $to $from"

    override fun apply(processor: Processor) {
        val a = to.read(processor)!!
        val b = from.read(processor)!!
        processor.setRegister(to, a.add(b))
    }
}

// Multiply the value of a by the value of b, then store the result in variable a.
class Mul(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "mul $to $from"

    override fun apply(processor: Processor) {
        val a = to.read(processor)!!
        val b = from.read(processor)!!
        processor.setRegister(to, a.mul(b))
    }
}

// Divide the value of a by the value of b, truncate the result to an integer, then store the result in variable a.
// (Here, "truncate" means to round the value toward zero.)
class Div(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "div $to $from"

    override fun apply(processor: Processor) {
        val a = to.read(processor)!!
        val b = from.read(processor)!!
        processor.setRegister(to, a.div(b))
    }
}

// Divide the value of a by the value of b, then store the remainder in variable a.
// (This is also called the modulo operation.)
class Mod(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "mod $to $from"

    override fun apply(processor: Processor) {
        val a = to.read(processor)!!
        val b = from.read(processor)!!
        processor.setRegister(to, a.mod(b))
    }
}

// If the value of a and b are equal, then store the value 1 in variable a.
class Eql(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "eql $to $from"

    override fun apply(processor: Processor) {
        val a = to.read(processor)!!
        val b = from.read(processor)!!
        processor.setRegister(to, a.eql(b))
    }
}
