package nl.ruudwiegers.adventofcode

import java.io.File
import java.net.URL
import kotlin.system.measureTimeMillis

abstract class AdventSolution(private val year: Int, private val day: Int, private val title: String = "gjhgh") {

    init {
        require(year in 2015..2100) { "$year is not a valid year. AoC started in 2015" }
        require(day in 1..25) { "$day is not a valid day in december. Choose a day in 1-25" }
    }

    fun solve() {
        val input = retrieveInput()

        println("Day $day: ${title.colored("32;1")}")

        var solution1 = ""
        var solution2 = ""
        val time1 = measureTimeMillis { solution1 = solvePartOne(input) }
        println("[${time1.toString().padStart(4).colored(gradeSolution(time1))} ms]  $solution1")
        val time2 = measureTimeMillis { solution2 = solvePartTwo(input) }
        println("[${time2.toString().padStart(4).colored(gradeSolution(time2))} ms]  $solution2")
        println()
        System.out.flush()
    }


    private fun String.colored(c:String) = "\u001B[${c}m$this\u001B[0m"

    private fun gradeSolution(time: Long) = when {
        time < 250L -> "32"
        time < 500L -> "33"
        else -> "31"
    }


    private fun retrieveInput() = readFromCache() ?: readfromUrl().also { write(it) }

    private fun readFromCache(): String? {
        val file = File(path)
        return if (file.exists()) {
            file.readText()
        } else null
    }

    private fun readfromUrl(): String {
        return URL("http://adventofcode.com/$year/day/$day/input")
                .openConnection()
                .apply { addRequestProperty("Cookie", "session=" + AOC_SESSION) }
                .getInputStream().use { it.readBytes() }
                .toString(Charsets.UTF_8)
                .trimEnd()
    }

    private fun write(input: String) {
        File(path).printWriter().use { out ->
            out.print(input)
        }
    }

    private val path get() = "aoc-$year-$day-input.txt"


    abstract fun solvePartOne(input: String): String
    abstract fun solvePartTwo(input: String): String
}

