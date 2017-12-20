package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import nl.ruudwiegers.adventofcode.y2017.Direction.*

object Day19 : AdventSolution(2017, 19) {

    override fun solvePartOne(input: String): String {
        val lines = input.split("\n")
        val pipeRunner = PipeRunner(lines)
        pipeRunner.run()
        return pipeRunner.word
    }

    override fun solvePartTwo(input: String): String {
        val lines = input.split("\n")
        val pipeRunner = PipeRunner(lines)
        pipeRunner.run()
        return pipeRunner.count.toString()
    }

}


private class PipeRunner(private val pipes: List<String>) {

    private var y = 0
    private var x = pipes[y].indexOf("|")

    var direction = DOWN

    var word = ""
        private set

    var count = 0
        private set

    fun run() {
        while (y in pipes.indices && x in pipes[y].indices) {
            action()
            step()
        }
    }

    private fun step() {
        when (direction) {
            UP ->    y--
            RIGHT -> x++
            DOWN ->  y++
            LEFT ->  x--
        }
        count++
    }

    private fun action() {
        when (pipes[y][x]) {
            '+' -> turn()
            in 'A'..'Z' -> word += pipes[y][x]
            ' ' -> {y = -10; count--} //end
        }

    }

    private fun turn() {
        direction = when {
            direction != DOWN  && y > 0                   && pipes[y - 1][x] == '|' -> UP
            direction != UP    && y < pipes.size - 1      && pipes[y + 1][x] == '|' -> DOWN
            direction != RIGHT && x > 0                   && pipes[y][x - 1] == '-' -> LEFT
            direction != LEFT  && x < pipes[y].length - 1 && pipes[y][x + 1] == '-' -> RIGHT
            else -> direction
        }
    }
}

private enum class Direction { UP, RIGHT, DOWN, LEFT }
