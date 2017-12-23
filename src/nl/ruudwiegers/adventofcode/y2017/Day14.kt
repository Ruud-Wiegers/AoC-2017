package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day14 : AdventSolution(2017, 14, "Disk Defragmentation") {

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

        var count = 0
        for (x in rows.indices) {
            for (y in rows[0].indices) {
                if (rows[y][x]) {
                    clearGroup(rows, x, y)
                    count++
                }
            }
        }
        return count.toString()
    }

    private fun clearGroup(rows: List<BooleanArray>, x: Int, y: Int) {
        if (rows.getOrNull(y)?.getOrNull(x) != true) {
            return
        }

        rows[y][x] = false

        clearGroup(rows, x + 1, y)
        clearGroup(rows, x - 1, y)
        clearGroup(rows, x, y + 1)
        clearGroup(rows, x, y - 1)
    }
}

private fun toBits(n: Int) = List(8) { n and (1 shl 7 - it) != 0 }
