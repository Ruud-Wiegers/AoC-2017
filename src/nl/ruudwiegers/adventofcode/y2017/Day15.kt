package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day15 : AdventSolution(2017, 15,"Dueling Generators") {

    override fun solvePartOne(input: String): String {
        val generatorSeeds: List<Long> = parseInput(input)

        val genA = generateSequence(generatorSeeds[0]) { it * 16807L % 2147483647L }
        val genB = generateSequence(generatorSeeds[1]) { it * 48271L % 2147483647L }

        return judge(genA, genB, 40_000_000).toString()
    }

    override fun solvePartTwo(input: String): String {
        val generatorSeeds = parseInput(input)

        val genA = generateSequence(generatorSeeds[0]) { it * 16807L % 2147483647L }.filter { it % 4L == 0L }
        val genB = generateSequence(generatorSeeds[1]) { it * 48271L % 2147483647L }.filter { it % 8L == 0L }

        return judge(genA, genB, 5_000_000).toString()
    }

    private fun parseInput(input: String): List<Long> = input.split("\n")
            .map { it.substringAfter("starts with ").toLong() }

    // This one's a bit subtle. A JVM Short has 16 bits, but is interpreted as signed two's complement.
    // There's definitely going to be overflow, so you really shouldn't be doing arithmetic with these.
    // Equality works fine though, and that's all we need.
    private fun judge(s0: Sequence<Long>, s1: Sequence<Long>, count: Int): Int = s0.zip(s1)
            .take(count)
            .count { (a, b) -> a.toShort() == b.toShort() }
}
