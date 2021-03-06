package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day24 : AdventSolution(2017, 24, "Electromagnetic Moat") {

    override fun solvePartOne(input: String): String {
        val pairs = parseInput(input)

        return buildAllBridges(emptyList(), pairs, 0)
                .map { it.sumBy { (a, b) -> a + b } }
                .max()
                .toString()
    }

    override fun solvePartTwo(input: String): String {
        val pairs = parseInput(input)

        return buildAllBridges(emptyList(), pairs, 0)
                .map { it.size to it.sumBy { (a, b) -> a + b } }
                .maxWith(compareBy({ it.first }, { it.second }))
                ?.second
                .toString()
    }

    private fun buildAllBridges(bridge: List<Pair<Int, Int>>, available: List<Pair<Int, Int>>, last: Int): List<List<Pair<Int, Int>>> = available
            .filter { it.first == last || it.second == last }
            .flatMap { buildAllBridges(bridge + it, available - it, if (last == it.first) it.second else it.first) }
            .let { if (it.isNotEmpty()) it else listOf(bridge) }


    private fun parseInput(input: String): List<Pair<Int, Int>> = input
            .split("\n")
            .map { it.split("/") }
            .map { it[0].toInt() to it[1].toInt() }

}