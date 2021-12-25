import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val iterator = Files.readAllLines(Path.of("input_short")).spliterator()
    val algorithm = Algorithm.parse(iterator)
    iterator.tryAdvance {
        // Drop blank line
    }
    val image = Image.parse(iterator)
    println(algorithm)
    println("")
    println(image)
    println("")
    var enhanced = image.enhance(algorithm)
    println(enhanced)
    println("")
    enhanced = enhanced.enhance(algorithm)
    println(enhanced)
    println("")
    println(enhanced.pixels.sumOf { row -> row.count { it } })
}