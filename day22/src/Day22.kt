import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val input = Files.readAllLines(Path.of("input")).map(::parseInstruction).toList()
    input.forEach {
        println(it)
    }
    val reactor = Reactor(50)
    input.forEach {
        reactor.apply(it.x, it.y, it.z, it.on)
    }
    println(reactor.countOn())
}

fun parseInstruction(input: String): Instruction {
    val parts = input.split(" ")
    val on = parts[0] == "on"
    val coords = parts[1].split(",")
    return Instruction(parseRange(coords[0]), parseRange(coords[1]), parseRange(coords[2]), on)
}

fun parseRange(input: String): IntRange {
    val parts = input.split("=")
    val idx = parts[1].indexOf("..")
    val from = parts[1].substring(0, idx).toInt()
    val to = parts[1].substring(idx + 2, parts[1].length).toInt()
    return IntRange(from, to)
}