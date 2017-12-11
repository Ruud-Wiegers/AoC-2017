package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import kotlin.math.abs

fun main(args: Array<String>) {
    Day11.solve()
}

object Day11 : AdventSolution(2017, 11) {
    override fun solvePartOne(input: String) = input.split(",")
            .map { HexCoordinates.fromDirection(it) }
            .reduce(HexCoordinates::plus)
            .distance
            .toString()

    //Sadly, Kotlin does not have a scan operator. Otherwise, we could do this fully in declarative style
    override fun solvePartTwo(input: String): String {
        var position = HexCoordinates(0, 0, 0)
        var distance = 0

        input.split(",")
                .map { HexCoordinates.fromDirection(it) }
                .forEach { step ->
                    position += step
                    distance = maxOf(distance, position.distance)
                }

        return distance.toString()
    }
}

//Cubic coordinates for hex grid. See https://www.redblobgames.com/grids/hexagons/
data class HexCoordinates(private val x: Int, private val y: Int, private val z: Int) {

    val distance get() = listOf(x, y, z).maxBy(::abs)!!

    infix operator fun plus(o: HexCoordinates) = HexCoordinates(x + o.x, y + o.y, z + o.z)

    companion object {
        //I still haven't found a good style for secondary constructors on data classes
        fun fromDirection(direction: String) = when (direction) {
            "n" -> HexCoordinates(0, 1, -1)
            "ne" -> HexCoordinates(1, 0, -1)
            "se" -> HexCoordinates(1, -1, 0)
            "s" -> HexCoordinates(0, -1, 1)
            "sw" -> HexCoordinates(-1, 0, 1)
            "nw" -> HexCoordinates(-1, 1, 0)
            else -> throw IllegalStateException(direction)
        }
    }
}
