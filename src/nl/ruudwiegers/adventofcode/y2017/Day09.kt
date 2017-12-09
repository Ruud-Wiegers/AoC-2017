package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day09 : AdventSolution {
    @JvmStatic
    fun main(args: Array<String>) {
        Day09.solve()
    }

    override val year = 2017
    override val day = 9

    override fun solvePartOne(input: String): String {
        val cleanedInput = input
                .replace("!.".toRegex(), "")
                .replace("<.*?>".toRegex(), "")

        var depth = 0
        var score = 0
        for (it in cleanedInput) when (it) {
            '{' -> depth++
            '}' -> score += depth--
        }

        return score.toString()
    }

    override fun solvePartTwo(input: String): String {
        val unignored = input.replace("!.".toRegex(), "")
        val cleaned = unignored.replace("<.*?>".toRegex(), "<>")
        return (unignored.length - cleaned.length).toString()
    }


}

