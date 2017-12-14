package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

object Day03 : AdventSolution(2017, 3) {

    override fun solvePartOne(input: String): String {
        val (x, y) = toSpiralCoordinates(input.toInt())
        return (abs(x) + abs(y) - 1).toString()
    }

    override fun solvePartTwo(input: String): String {
        val spiralCoordinatesSequence = generateSequence(1) { it + 1 }
                .map { toSpiralCoordinates(it) }

        val spiral = mutableMapOf(Pair(0, 0) to 1)

        val spiralSummatorySequence = spiralCoordinatesSequence.map { coordinate ->
            coordinate.neighbors()
                    .sumBy { neighbor -> spiral[neighbor] ?: 0 }
                    .also { spiral[coordinate] = it }
        }

        return spiralSummatorySequence
                .find { it > input.toInt() }
                .toString()
    }

    private fun Pair<Int, Int>.neighbors() =
            (-1..1).flatMap { x ->
                (-1..1).map { y ->
                    Pair(first + x, second + y)
                }
            }

    private fun toSpiralCoordinates(index: Int): Pair<Int, Int> {
        if (index <= 0) return Pair(0, 0)

        //The width of the edge at the current edge of the spiral
        val width = Math.round(sqrt(index.toDouble())).toInt()

        //the index of the end of the preceding completed half-turn
        val spiralBaseIndex = width * (width - 1)

        // 1 or -1, depending on if we've done a complete turn or a half-turn
        val sign = width.sign

        //distance from origin: the position of spiralBaseIndex is [offset,offset]
        val offset = sign * (width / 2)

        //corner in the middle of the current half-turn
        val cornerIndex = spiralBaseIndex + width

        val x = offset - sign * (index - spiralBaseIndex).coerceAtMost(width)
        val y = offset - sign * (index - cornerIndex).coerceAtLeast(0)

        return Pair(x, y)
    }
}