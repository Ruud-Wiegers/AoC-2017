package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

private val startingConfiguration = ".#./..#/###".split('/')

object Day21 : AdventSolution(2017, 21) {

    override fun solvePartOne(input: String) = puzzle(input, 5).toString()

    override fun solvePartTwo(input: String) = puzzle(input, 18).toString()

    private fun puzzle(input: String, i: Int): Int {
        val rules = parseRewriteRules(input)
        val fullRules = expandSymmetries(rules)

        var grid = startingConfiguration
        repeat(i) { grid = step(grid, fullRules) }

        return grid.sumBy { line ->
            line.count { char ->
                char == '#'
            }
        }
    }
}


private fun parseRewriteRules(input: String): Map<Square, Square> = input.split("\n")
        .map { it.split(" => ") }
        .associate { (k, v) ->
            k.split("/") to v.split("/")
        }

private fun expandSymmetries(input: Map<Square, Square>): Map<Square, Square> = input
        .flatMap { entry -> symmetries(entry.key).map { it to entry.value } }
        .toMap()

private fun symmetries(pattern: Square): List<Square> {
    val rotations = generateSequence(pattern, ::rotate).take(4).asIterable()
    return rotations + rotations.map { it.reversed() }
}

private fun rotate(list: Square): Square {
    return list.indices.map { i ->
        list[0].indices.reversed().map { j ->
            list[j][i]
        }.joinToString("")
    }
}


private fun step(grid: Square, fullRules: Map<Square, Square>): Square {
    val squareSize = if (grid[0].length % 2 == 0) 2 else 3
    val squares = splitToSquares(grid, squareSize)
    val enhancedSquares = replaceSquares(squares, fullRules)
    return combineSquaresToGrid(enhancedSquares)
}

private fun splitToSquares(grid: Square, size: Int): List<List<Square>> =
        grid.chunked(size) { chunkRow ->
            chunkRow.map { line -> line.chunked(size) }
                    .transpose()
        }

private fun replaceSquares(chunkedGrid: List<List<Square>>, rules: Map<Square, Square>) =
        chunkedGrid.map { row ->
            row.map { square ->
                rules[square]!!
            }
        }

private fun combineSquaresToGrid(chunkedGrid: List<List<Square>>): Square =
        chunkedGrid.flatMap { chunkRow ->
            chunkRow.transpose().map { it.joinToString("") }
        }

private inline fun <reified T> List<List<T>>.transpose() =
        this[0].indices.map { col ->
            map { it[col] }
        }


typealias Square = List<String>