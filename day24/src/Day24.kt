import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val instructions = Files.readAllLines(Path.of("input"))
        .filterNot { it.isBlank() }
        .map(::parseInstruction)
    val processor = Processor()
    instructions.forEach {
        println(it)
        it.apply(processor)
        // println(processor)
        // println("")
    }
}

fun parseInstruction(input: String): Instruction {
    val parts = input.split(" ")
    require(parts.size in 2..3)
    return when (parts[0]) {
        "inp" -> {
            Input(Register.valueOf(parts[1]))
        }
        "add" -> {
            Add(Register.valueOf(parts[1]), RegisterOrConstant.parse(parts[2]))
        }
        "mul" -> {
            Mul(Register.valueOf(parts[1]), RegisterOrConstant.parse(parts[2]))
        }
        "div" -> {
            Div(Register.valueOf(parts[1]), RegisterOrConstant.parse(parts[2]))
        }
        "mod" -> {
            Mod(Register.valueOf(parts[1]), RegisterOrConstant.parse(parts[2]))
        }
        "eql" -> {
            Eql(Register.valueOf(parts[1]), RegisterOrConstant.parse(parts[2]))
        }
        else -> {
            throw RuntimeException("Unrecognised command $input")
        }
    }
}