package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import kotlin.math.sqrt

object Day03 : AdventSolution {
    @JvmStatic
    fun main(args: Array<String>) {
        Day03.solve()
    }

    override val year = 2017
    override val day = 3

    override fun solvePartOne(input: String): String {

        val x = input.toDouble()
        val ring = sqrt(x).toInt() / 2
       // println(ring)
        val rem = x - (2 * ring+1) * (2*ring+1)
        return (ring+ring+rem).toString()

    }


    override fun solvePartTwo(input: String) :String{

        val list = mutableListOf(1,1,2,4,5,10)

        return "k"
    }



}