import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val iterator = Files.readAllLines(Path.of("input")).spliterator()
    val algorithm = Algorithm.parse(iterator)
    iterator.tryAdvance {
        // Drop blank line
    }
    val image = RawImage.parse(iterator)
    println(algorithm)
    println("")
    println(image)
    println("")
    var enhanced = Enhance(image, algorithm)
    println(enhanced)
    println("")
    enhanced = Enhance(enhanced, algorithm)
    println(enhanced)
    println("")
    println(enhanced.toString().toCharArray().count { it == '#' })
}