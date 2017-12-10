package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day01 : AdventSolution(2017, 1) {

    override fun solvePartOne(input: String): String {
        val pairs = (input + input[0]).zipWithNext()
        return sumOfEqualPairs(pairs)
    }

    override fun solvePartTwo(input: String): String {
        val shiftedInput = input.shiftRight(input.length / 2)
        val pairs = input.zip(shiftedInput)
        return sumOfEqualPairs(pairs)
    }

    private fun sumOfEqualPairs(pairs: List<Pair<Char, Char>>): String = pairs
            .filter { (a, b) -> a == b }
            .sumBy { Character.getNumericValue(it.first) }
            .toString()

    private fun String.shiftRight(n: Int) = substring(n) + substring(0 until n)
}