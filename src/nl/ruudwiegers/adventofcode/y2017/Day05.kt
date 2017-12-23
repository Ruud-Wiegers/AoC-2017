package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day05 : AdventSolution(2017, 5,"A Maze of Twisty Trampolines, All Alike") {

    override fun solvePartOne(input: String): String {
        val jumpmap = parse(input)
        return countJumps(jumpmap) { it + 1 }
                .toString()
    }

    override fun solvePartTwo(input: String): String {
        val jumpmap = parse(input)
        return countJumps(jumpmap) { if (it < 3) it + 1 else it - 1 }
                .toString()
    }

    private fun parse(input: String): IntArray = input
            .split("\n")
            .map { it.toInt() }
            .toIntArray()


    private inline fun countJumps(jumpmap: IntArray, modification: (Int) -> Int): Int {
        var count = 0
        var pos = 0

        while (pos in jumpmap.indices) {

            val oldPos = pos
            pos += jumpmap[pos]
            jumpmap[oldPos] = modification(jumpmap[oldPos])
            count++
        }
        return count
    }
}
