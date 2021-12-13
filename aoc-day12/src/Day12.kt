import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val vertices = Files.readAllLines(Path.of("input"))
        .filter { it.isNotBlank() }
        .map { Vertex.parse(it) }
    val graph = Graph.build(vertices)
    val paths = part2(graph.start, setOf(graph.start), null)
    paths.forEach { path ->
        println(path.joinToString(",") { node -> node.id })
    }
    println(paths.toSet().size)
}

fun part1(from: Node, visited: Set<Node>): List<List<Node>> {
    if (from.id == "end") {
        return listOf(listOf(from))
    }
    val paths = mutableListOf<List<Node>>()
    from.exits().forEach { next ->
        if (!visited.contains(next)) {
            if (next.isSmall()) {
                paths.addAll(part1(next, visited + next).map { listOf(from) + it })
            } else {
                paths.addAll(part1(next, visited).map { listOf(from) + it })
            }
        }
    }
    return paths
}

fun part2(from: Node, visitedOnce: Set<Node>, visitedTwice: Node?): List<List<Node>> {
    if (from.id == "end") {
        return listOf(listOf(from))
    }
    val paths = mutableListOf<List<Node>>()
    from.exits().forEach { next ->
        if (!visitedOnce.contains(next)) {
            if (next.isSmall()) {
                paths.addAll(part2(next, visitedOnce + next, visitedTwice).map { listOf(from) + it })
            } else {
                paths.addAll(part2(next, visitedOnce, visitedTwice).map { listOf(from) + it })
            }
        }
        if (visitedTwice == null) {
            if (next.isSmall()) {
                paths.addAll(part2(next, visitedOnce + next, from).map { listOf(from) + it })
            } else {
                paths.addAll(part2(next, visitedOnce, from).map { listOf(from) + it })
            }
        }
    }
    return paths
}