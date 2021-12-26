import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val instructions = Files.readAllLines(Path.of("input"))
        .filterNot { it.isBlank() }
        .map(::parseInstruction)
    val processor = Processor()
    part1(processor, instructions)
}

fun part1(processor: Processor, instructions: List<Instruction>) {
    val result = process(ProcessorThread(processor, 0), instructions)
    println(result.second.joinToString(""))
}

val cache = mutableMapOf<ProcessorThread, Pair<Boolean, List<Int>>>()

fun process(thread: ProcessorThread, instructions: List<Instruction>): Pair<Boolean, List<Int>> {
    val processor = thread.processor
    val startIdx = thread.startIdx
    for (i in startIdx until instructions.size) {
        when (val ins = instructions[i]) {
            is Input -> {
                // Fork 9 ways
                for (fork in 9 downTo 1) {
                    val subprocessor = processor.fork(i + 1)
                    subprocessor.processor.set(ins.to, fork)
                    val outcome = if (cache.containsKey(subprocessor)) {
                        cache[subprocessor]!!
                    } else {
                        process(subprocessor, instructions).also {
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
                processor.set(ins.to, ins.to.read(processor) + ins.from.read(processor))
            }
            is Mul -> {
                processor.set(ins.to, ins.to.read(processor) * ins.from.read(processor))
            }
            is Div -> {
                processor.set(ins.to, ins.to.read(processor) / ins.from.read(processor))
            }
            is Mod -> {
                processor.set(ins.to, ins.to.read(processor) % ins.from.read(processor))
            }
            is Eql -> {
                processor.set(
                    ins.to, if (ins.to.read(processor) == ins.from.read(processor)) {
                        1
                    } else {
                        0
                    }
                )
            }
        }
    }
    return if (processor.get(Register.z) == 0) {
        Pair(true, emptyList())
    } else {
        Pair(false, emptyList())
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