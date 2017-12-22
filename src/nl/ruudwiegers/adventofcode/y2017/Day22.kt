package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day22 : AdventSolution(2017, 22) {

    override fun solvePartOne(input: String): String {
        val centeredMap = parseInput(input)

        var position = Point(0, 0)
        var direction = directions[0]
        var infectionsCount = 0

        repeat(10000) {
            if (centeredMap[position] == '#') {
                direction = direction.turn(1)
                centeredMap[position] = '.'
            } else {
                direction = direction.turn(-1)
                centeredMap[position] = '#'
                infectionsCount++
            }
            position += direction
        }
        return infectionsCount.toString()
    }

    override fun solvePartTwo(input: String): String {
        val centeredMap = parseInput(input)


        var direction = Point(0, -1)
        var position = Point(0, 0)
        var infected = 0

        repeat(10_000_000) {
            direction = when (centeredMap[position]) {
                '.', null -> direction.turn(-1)
                'W' -> direction
                '#' -> direction.turn(1)
                'F' -> direction.turn(2)
                else -> throw IllegalStateException()
            }
            centeredMap[position] = when (centeredMap[position]) {
                '.', null -> 'W'
                'W' -> '#'
                '#' -> 'F'
                'F' -> '.'
                else -> throw IllegalStateException()
            }
            if (centeredMap[position] == '#') infected++
            position += direction
        }
        return infected.toString()
    }

    private fun parseInput(input: String): MutableMap<Point, Char> {
        return input.split("\n")
                .mapIndexed { y, row ->
                    row.mapIndexed { x, char ->
                        Point(x - row.length / 2, y - row.length / 2) to char
                    }
                }
                .flatten().toMap().toMutableMap()
    }

    private val directions = listOf(Point(0, -1), Point(1, 0), Point(0, 1), Point(-1, 0))

    private data class Point(val x: Int, val y: Int) {
        operator fun plus(o: Point) = Point(x + o.x, y + o.y)
        fun turn(i: Int) = directions[(directions.size + directions.indexOf(this) + i) % directions.size]
    }
}
