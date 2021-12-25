import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val iterator = Files.readAllLines(Path.of("input")).spliterator()
    val algorithm = Algorithm.parse(iterator)
    iterator.tryAdvance {
        // Drop blank line
    }
    var image: Image = RawImage.parse(iterator)
    for (i in 1..50) {
        image = Enhance(image, algorithm)
    }
    // println(image)
    println(image.toString().toCharArray().count { it == '#' })
}