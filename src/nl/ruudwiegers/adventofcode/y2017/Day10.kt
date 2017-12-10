package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day10 : AdventSolution {
    @JvmStatic
    fun main(args: Array<String>) {
        Day10.solve()
    }

    override val year = 2017
    override val day = 10

    override fun solvePartOne(input: String): String {
        val lengths = input.split(",").map { it.toInt() }

        val list = knotHash(lengths)
        return (list[0] * list[1]).toString()
    }

    override fun solvePartTwo(input: String): String {
        val lengths = input.map { it.toInt() } + listOf(17, 31, 73, 47, 23)
        val rounds = (1..64).flatMap { lengths }

        val sparseHash = knotHash(rounds)
        val denseHash = sparseHash.chunked(16) { it.reduce(Int::xor) }
        return denseHash.joinToString("") { it.toString(16).padStart(2, '0') }
    }

    private fun knotHash(lengths: List<Int>): List<Int> {
        val initial = (0..255).toList()
        val list = lengths.foldIndexed(initial) { skip, list, length ->
            val knot = list.drop(length) + list.take(length).reversed()
            knot.rotateLeft(skip)
        }

        //undo all previous rotations
        val rotation = lengths.withIndex().sumBy { (i, l) -> i + l } % list.size
        return list.rotateLeft(list.size - rotation)
    }

    private fun List<Int>.rotateLeft(skip: Int) = drop(skip % size) + take(skip % size)

}