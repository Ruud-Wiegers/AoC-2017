package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import nl.ruudwiegers.adventofcode.y2017.Day22.Health.*
import kotlin.system.measureTimeMillis

object Day22 : AdventSolution(2017, 22) {

    override fun solvePartOne(input: String): String {
        val centeredMap = parseInput(input)
        var position = Point(0, 0)
        var direction = Direction.UP
        var infectionsCount = 0

        repeat(10000) {
            val health = centeredMap[position] ?: HEALTHY
            if (health == INFECTED) {
                direction = direction.right()
                centeredMap[position] = HEALTHY
            } else {
                direction = direction.left()
                centeredMap[position] = INFECTED
                infectionsCount++
            }
            position += direction.vector
        }
        return infectionsCount.toString()
    }

    override fun solvePartTwo(input: String): String {
        val centeredMap = parseInput(input)

        var direction = Direction.UP
        var position = Point(0, 0)
        var infectionsCount = 0

        repeat(10_000_000) {
            val health = centeredMap[position] ?: HEALTHY
            direction = when (health) {
                HEALTHY -> direction.left()
                WEAKENED -> direction
                INFECTED -> direction.right()
                FLAGGED -> direction.reverse()
            }
            if (health == WEAKENED) infectionsCount++

            centeredMap[position] = health.next()
            position += direction.vector
        }
        return infectionsCount.toString()
    }

    private fun parseInput(input: String): MutableMap<Point, Health> {
        return input.split("\n")
                .mapIndexed { y, row ->
                    row.mapIndexed { x, char ->
                        val position = Point(x - row.length / 2, y - row.length / 2)
                        val state = if (char == '#') INFECTED else HEALTHY
                        position to state
                    }
                }
                .flatten().toMap().toMutableMap()
    }

    private enum class Health {
        HEALTHY, WEAKENED, INFECTED, FLAGGED;

        fun next() = values().let { it[(it.indexOf(this) + 1) % it.size] }
    }

    private enum class Direction(val vector: Point) {
        UP(Point(0, -1)), RIGHT(Point(1, 0)), DOWN(Point(0, 1)), LEFT(Point(-1, 0));

        fun left() = turn(3)
        fun right() = turn(1)
        fun reverse() = turn(2)

        private fun turn(i: Int): Direction = Direction.values().let { it[(it.indexOf(this) + i) % it.size] }
    }

    private data class Point(val x: Int, val y: Int) {
        operator fun plus(o: Point) = Point(x + o.x, y + o.y)
    }
}