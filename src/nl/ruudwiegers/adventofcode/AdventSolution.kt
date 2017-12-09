package nl.ruudwiegers.adventofcode

interface AdventSolution {
    val year: Int
    val day: Int

    fun solve() {
        val input = AdventOfCodeApi.retrieveInput(day, year)
        val d = day.toString().padStart(2)
        print("$d December $year : ")
        print(solvePartOne(input).padStart(10))
        print(solvePartTwo(input).padStart(12))
        println()
    }

    fun solvePartOne(input: String): String
    fun solvePartTwo(input: String): String
}

