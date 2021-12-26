sealed interface Instruction

// Read an input value and write it to variable a.
class Input(val to: Register) : Instruction {
    override fun toString() = "inp $to"
}

// Add the value of a to the value of b, then store the result in variable a.
class Add(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "add $to $from"
}

// Multiply the value of a by the value of b, then store the result in variable a.
class Mul(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "mul $to $from"
}

// Divide the value of a by the value of b, truncate the result to an integer, then store the result in variable a.
// (Here, "truncate" means to round the value toward zero.)
class Div(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "div $to $from"
}

// Divide the value of a by the value of b, then store the remainder in variable a.
// (This is also called the modulo operation.)
class Mod(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "mod $to $from"
}

// If the value of a and b are equal, then store the value 1 in variable a.
class Eql(val to: Register, val from: RegisterOrConstant): Instruction {
    override fun toString() = "eql $to $from"
}
