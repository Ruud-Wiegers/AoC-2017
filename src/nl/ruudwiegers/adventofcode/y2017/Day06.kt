package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day06 : AdventSolution(2017, 6,"Memory Reallocation") {

    override fun solvePartOne(input: String): String {
        val state = parse(input)
        val history = mutableSetOf<List<Int>>()
        return generateSequence(state) { redistribute(it) }
                .takeWhile { it !in history }
                .onEach { history += it }
                .count()
                .toString()
    }

    override fun solvePartTwo(input: String): String {
        val state = parse(input)
        val history = mutableSetOf<List<Int>>()
        val seen = generateSequence(state) { redistribute(it) }
                .takeWhile { it !in history }
                .onEach { history += it }
                .last()

        val s = generateSequence(seen) { redistribute(it) }
                .drop(1)
                .takeWhile { it != seen }
                .count() + 1

        return s.toString()
    }

    private fun redistribute(state: List<Int>): List<Int> {
        val max = state.max()!!
        val i = state.indexOf(max)
        val newState = state.map { it }.toMutableList()
        newState[i] = 0
        ((i + 1)..i + max).forEach {
            newState[it % newState.size]++
        }
        return newState
    }

    private fun parse(input: String) = input
            .split("\t")
            .map { it.toInt() }
}
