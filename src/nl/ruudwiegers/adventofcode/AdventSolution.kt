package nl.ruudwiegers.adventofcode

abstract class AdventSolution(val year: Int, val day: Int) {

    fun solve() {
        val input = AdventOfCodeApi.retrieveInput(day, year)
        val d = day.toString().padStart(2)
        println("$d December $year : ")
        println("  1. " + solvePartOne(input))
        println("  2. " + solvePartTwo(input))
    }

    abstract fun solvePartOne(input: String): String
    abstract fun solvePartTwo(input: String): String
}

