package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import nl.ruudwiegers.adventofcode.retrieveInput
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    listOf(Day01, Day02, Day03, Day04, Day05,
            Day06, Day07, Day08, Day09, Day10,
            Day11, Day12, Day13, Day14, Day15,
            Day16, Day17, Day18, Day19, Day20,
            Day21, Day22, Day23, Day24, Day25)
            .forEach { solve(it) }
}

fun solve(s: AdventSolution) {
    val input = retrieveInput(s.day, s.year)

    println("Day ${s.day}: ${s.title.colored("32;1")}")

    var solution1 = ""
    var solution2 = ""
    val time1 = measureTimeMillis { solution1 = s.solvePartOne(input) }
    println("[${time1.toString().padStart(4).colored(gradeSolution(time1))} ms]  $solution1")
    val time2 = measureTimeMillis { solution2 = s.solvePartTwo(input) }
    println("[${time2.toString().padStart(4).colored(gradeSolution(time2))} ms]  $solution2")
    println()
    System.out.flush()
}


private fun String.colored(c: String) = "\u001B[${c}m$this\u001B[0m"

private fun gradeSolution(time: Long) = when {
    time < 250L -> "32"
    time < 500L -> "33"
    else -> "31"
}
