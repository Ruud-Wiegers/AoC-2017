package nl.ruudwiegers.adventofcode

abstract class AdventSolution( val year: Int,  val day: Int,  val title: String) {

    init {
        require(year in 2015..2100) { "$year is not a valid year. AoC started in 2015" }
        require(day in 1..25) { "$day is not a valid day in december. Choose a day in 1-25" }
    }

    abstract fun solvePartOne(input: String): String
    abstract fun solvePartTwo(input: String): String
}

