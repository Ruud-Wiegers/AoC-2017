package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day04 : AdventSolution {
    @JvmStatic
    fun main(args: Array<String>) {
        Day04.solve()
    }

    override val year = 2017
    override val day = 4

    override fun solvePartOne(input: String) = input
            .split("\n")
            .map { it.split(" ") }
            .count { it.distinct().size == it.size }
            .toString()

    override fun solvePartTwo(input: String) = input
            .split("\n")
            .map { it.split(" ").map { it.toList().sorted() } }
            .count { it.distinct().size == it.size }
            .toString()

}
