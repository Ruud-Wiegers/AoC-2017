package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day14 : AdventSolution(2017, 14) {

    override fun solvePartOne(input: String): String = (0..127)
            .map { "$input-$it" }
            .flatMap { knotHash(it) }
            .flatMap { toBits(it) }
            .count { it }
            .toString()

    override fun solvePartTwo(input: String): String {

        val rows = (0..127)
                .map { "$input-$it" }
                .map { knotHash(it) }
                .map { hash -> hash.flatMap { toBits(it) }.toBooleanArray() }

        return rows.indices.asSequence()
                .flatMap { y ->
                    rows[0].indices.asSequence()
                            .map { x -> Pair(x, y) }
                }
                .filter { (x, y) -> rows[y][x] }
                .onEach { clearGroup(rows, it) }
                .count()
                .toString()
    }

    private fun clearGroup(rows: List<BooleanArray>, c: Pair<Int, Int>) {
        rows[c.second][c.first] = false
        c.neighbors()
                .filter { (x, y) ->
                    x in rows[0].indices
                            && y in rows.indices
                            && rows[y][x]
                }
                .forEach { n -> clearGroup(rows, n) }
    }
}

private fun toBits(n: Int) = List(8) { n and (1 shl 7 - it) != 0 }

private fun Pair<Int, Int>.neighbors() = listOf(
        Pair(first + 1, second),
        Pair(first - 1, second),
        Pair(first, second + 1),
        Pair(first, second - 1)
)
