package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import kotlin.math.abs
import kotlin.math.sqrt

object Day03 : AdventSolution {
    @JvmStatic
    fun main(args: Array<String>) {
        Day03.solve()
    }

    override val year = 2017
    override val day = 3

    override fun solvePartOne(input: String): String {
        val (x, y) = toCoordinates(input.toInt())
        return (abs(x) + abs(y) - 1).toString()
    }

    override fun solvePartTwo(input: String): String {

        val spiral = Array(30) { IntArray(30) }
        val offset = 15
        spiral[offset][offset] = 1

        return (1..60)
                .map { i ->

                    val (x, y) = toCoordinates(i).let { (x, y) -> x + offset to y + offset }

                    spiral[x][y] = (x to y)
                            .neighbors()
                            .sumBy { (xx, yy) -> spiral[xx][yy] }

                    spiral[x][y]
                }
                .find { it > input.toInt() }
                .toString()
    }

    private fun Pair<Int, Int>.neighbors(): List<Pair<Int, Int>> {
        val xs = listOf(first - 1, first, first + 1)
        val ys = listOf(second - 1, second, second + 1)
        return xs.flatMap { x -> ys.map { y -> x to y } }
    }

    //sorry
    private fun toCoordinates(n: Int): Pair<Int, Int> {
        if (n <= 0) return 0 to 0

        val v = (sqrt(n - .75) - .5).toInt()
        val spiralBaseIndex = v * (v + 1)
        val sign = v % 2 * 2 - 1 // sign == v even
        val offset = sign * ((v + 1) / 2)
        val cornerIndex = spiralBaseIndex + v + 1

        return if (n <= cornerIndex) {
            val x = offset - sign * (n - spiralBaseIndex)
            x to offset
        } else {
            val x = offset - sign * (v + 1)
            val y = offset - sign * (n - cornerIndex)
            x to y
        }

    }
}