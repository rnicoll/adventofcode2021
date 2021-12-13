import java.nio.file.Files
import java.nio.file.Path

fun main() {
    // Load the file
    val instructions: List<Instruction> = Files.readAllLines(Path.of("input")).mapNotNull { line ->
        if (line.trim().isEmpty()) {
             null;
        } else {
            val parts = line.trim().split(" ");
            val direction = parts[0].toLowerCase();
            val count = Integer.valueOf(parts[1]);
            Instruction(direction, count);
        }
    };
    part1(instructions);
    part2(instructions);
}

fun part1(instructions: Iterable<Instruction>) {
    // Process the input
    var depth = 0;
    var horizontal = 0;
    instructions.forEach { instruction ->
        when (instruction.direction) {
            "forward" ->
                horizontal += instruction.count;
            "up" -> {
                depth -= instruction.count;
                if (depth < 0) {
                    println("Submarine trying to fly?");
                }
            }
            "down" ->
                depth += instruction.count;
            else -> println("Unrecognised direction ${instruction.direction}")
        }
    }
    println("Depth: $depth, horizontal: $horizontal")
    println("Result: " + (depth * horizontal));
}

fun part2(instructions: Iterable<Instruction>) {
    // Process the input
    var depth = 0;
    var aim = 0;
    var horizontal = 0;
    instructions.forEach { instruction ->
        when (instruction.direction) {
            "forward" -> {
                horizontal += instruction.count;
                depth += (aim * instruction.count);
            }
            "up" -> {
                aim -= instruction.count;
            }
            "down" -> {
                aim += instruction.count;
            }
            else -> println("Unrecognised direction ${instruction.direction}")
        }
    }
    println("Depth: $depth, horizontal: $horizontal")
    println("Result: " + (depth * horizontal));
}