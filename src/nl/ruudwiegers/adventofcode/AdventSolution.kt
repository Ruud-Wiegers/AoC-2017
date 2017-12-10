package nl.ruudwiegers.adventofcode

interface AdventSolution {
    val year: Int
    val day: Int

    fun solve() {
        val input = AdventOfCodeApi.retrieveInput(day, year)
        val d = day.toString().padStart(2)
        println("$d December $year : ")
        println("  1. " + solvePartOne(input))
        println("  2. " + solvePartTwo(input))
    }

    fun solvePartOne(input: String): String
    fun solvePartTwo(input: String): String
}

