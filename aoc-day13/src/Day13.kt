import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val parts = Files.readString(Path.of("input"))
    val split = parts.indexOf("\n\n")
    require(split > 0)
    val grid = Grid.parse(parts.substring(0 until split).split("\n"))
    val folds = parts.substring(split until parts.length).trim().split("\n").map {
        Fold.parse(it)
    }

    part1(grid, folds.first())
    part2(grid, folds)
}

fun part1(grid: Grid, fold: Fold) {
    val foldedGrid = fold.apply(grid)
    val total = foldedGrid.dots.sumOf { row -> row.filter { it }.count() }
    println(total)
}

fun part2(grid: Grid, folds: Iterable<Fold>) {
    var currentGrid = grid
    folds.forEach {
        currentGrid = it.apply(currentGrid)
        println(currentGrid)
        println("")
    }
}