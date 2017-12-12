package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day12 : AdventSolution(2017, 12) {

    override fun solvePartOne(input: String): String {
        val connections = parseInput(input)
        val group = findGroup(connections, 0)

        return group.size.toString()
    }

    override fun solvePartTwo(input: String): String {
        var connections = parseInput(input)

        var count = 0
        while (connections.isNotEmpty()) {
            val group = findGroup(connections, connections.keys.first())
            connections -= group
            count++
        }
        return count.toString()
    }

    private fun parseInput(input: String): Map<Int, List<Int>> = input.split("\n")
            .map { line ->
                val program = line.substringBefore(" <->").toInt()
                val connections = line.substringAfter("<-> ").split(", ").map { it.toInt() }
                program to connections
            }
            .toMap()

    private fun findGroup(connections: Map<Int, List<Int>>, firstElement: Int): MutableSet<Int> {
        val visited = mutableSetOf<Int>()
        val group = mutableSetOf(firstElement)

        while (group.any { it !in visited }) {
            group.filter { it !in visited }
                    .forEach {
                        visited += it
                        group += connections[it] ?: emptyList()
                    }
        }
        return group
    }
}