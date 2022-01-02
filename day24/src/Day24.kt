import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val instructions = Files.readAllLines(Path.of("input"))
        .filterNot { it.isBlank() }
        .map(::parseInstruction)
    val processor = Alu()
    // part1(processor, instructions)
    part2(processor, instructions)
}

fun part1(processor: Alu, instructions: List<Instruction>) {
    val result = processor.process(instructions, listOf(9, 8, 7, 6, 5, 4, 3, 2, 1))
    println(result.second.joinToString(""))
}

fun part2(processor: Alu, instructions: List<Instruction>) {
    val result = processor.process(instructions, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
    println(result.second.joinToString(""))
}

val cache = mutableMapOf<Alu, Pair<Boolean, List<Int>>>()

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