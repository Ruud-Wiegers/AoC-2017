package nl.ruudwiegers.adventofcode

import java.io.File
import java.net.URL

abstract class AdventSolution(private val year: Int, private val day: Int) {

    init {
        require(year in 2015..2100) { "$year is not a valid year. AoC started in 2015" }
        require(day in 1..25) { "$day is not a valid day in december. Choose a day in 1-25" }
    }

    fun solve() {
        val input = retrieveInput()
        val d = day.toString().padStart(2)
        println("$d December $year:")
        println("   1. " + solvePartOne(input))
        println("   2. " + solvePartTwo(input))
        println()
    }

    private fun retrieveInput() = readFromCache() ?: readfromUrl().also { write(it) }


    private fun readfromUrl(): String {
        return URL("http://adventofcode.com/$year/day/$day/input")
                .openConnection()
                .apply { addRequestProperty("Cookie", "session=" + AOC_SESSION) }
                .getInputStream().use { it.readBytes() }
                .toString(Charsets.UTF_8)
                .trimEnd()
    }

    private fun write(input: String) {
        File("aoc-$year-$day-input.txt").printWriter().use { out ->
            out.print(input)
        }
    }

    private fun readFromCache(): String? {
        val file = File("aoc-$year-$day-input.txt")
        return if (file.exists()) {
            file.readText()
        } else null
    }

    abstract fun solvePartOne(input: String): String
    abstract fun solvePartTwo(input: String): String
}

