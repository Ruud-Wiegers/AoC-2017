package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import kotlin.math.sqrt

fun main(args: Array<String>) {
    Day21.solve()
}

object Day21 : AdventSolution(2017, 21) {

    override fun solvePartOne(input: String): String {
        return puzzle(input, 5)
    }

    override fun solvePartTwo(input: String): String {
        return puzzle(input, 18)
    }

    private fun puzzle(input: String, i: Int): String {
        val (map2, map3) = parseRewriteRules(input)
        val fullMap2 = expandSymmetries(map2)
        val fullMap3 = expandSymmetries(map3)

        var configuration = ".#./..#/###"
        repeat(i) {
            configuration = step(configuration, fullMap2, fullMap3)
        }

        return configuration.count { it == '#' }.toString()
    }

    private fun step(configuration: String, fullMap2: Map<String, String>, fullMap3: Map<String, String>): String {
        val lengthIsEven = configuration.substringBefore("/").length % 2 == 0
        return if (lengthIsEven) {
            evenIteration(configuration, fullMap2)
        } else {
            oddIteration(configuration, fullMap3)
        }
    }
}

private fun parseRewriteRules(input: String): Pair<Map<String, String>, Map<String, String>> {
    val (rule2, rule3) = input.split("\n")
            .map { it.split(" => ") }
            .map { (k, v) -> k to v }
            .partition { it.first.length == 5 }

    return Pair(rule2.toMap(), rule3.toMap())
}

private fun expandSymmetries(input: Map<String, String>): Map<String, String> = input.toMutableMap().apply {
    input.forEach { (k, v) ->
        this[rotate(k)] = v
        this[rotate(rotate(k))] = v
        this[rotate(rotate(rotate(k)))] = v
        this[flip(k)] = v
        this[flip(rotate(k))] = v
        this[flip(rotate(rotate(k)))] = v
        this[flip(rotate(rotate(rotate(k))))] = v
    }
}

private fun flip(input: String) = input.split('/').joinToString("/") { it.reversed() }

private fun rotate(input: String) = buildString {
    val grid = input.split('/')
    for (i in grid.indices) {
        for (j in grid[0].indices.reversed()) {
            this.append(grid[j][i])
        }
        if (i != grid.lastIndex) this.append('/')
    }
}

private fun evenIteration(configuration: String, map2: Map<String, String>): String {

    val chunks: List<String> = configuration
            .split("/")
            .chunked(2)
            .flatMap { (a, b) ->
                a.chunked(2)
                        .zip(b.chunked(2))
                        .map { (a, b) -> "$a/$b" }
            }


    val rowLength = sqrt(chunks.size.toDouble()).toInt()

    val new = chunks
            .map { map2[it]!!.split('/') }
            .chunked(rowLength)
            .map { row ->
                val line0 = row.joinToString("") { chunk -> chunk[0] }
                val line1 = row.joinToString("") { chunk -> chunk[1] }
                val line2 = row.joinToString("") { chunk -> chunk[2] }
                "$line0/$line1/$line2"
            }

    return new.joinToString("/")
}

private fun oddIteration(configuration: String, map3: Map<String, String>): String {
    val chunks = configuration
            .split("/")
            .chunked(3)
            .flatMap { (a, b, c) ->
                a.chunked(3)
                        .zip(b.chunked(3))
                        .zip(c.chunked(3))
                        .map { (a, b) -> "${a.first}/${a.second}/$b" }
            }

    val rowLength = sqrt(chunks.size.toDouble()).toInt()

    val new = chunks
            .map { map3[it]!!.split('/') }
            .chunked(rowLength)
            .map { row ->
                val line0 = row.joinToString("") { chunk -> chunk[0] }
                val line1 = row.joinToString("") { chunk -> chunk[1] }
                val line2 = row.joinToString("") { chunk -> chunk[2] }
                val line3 = row.joinToString("") { chunk -> chunk[3] }

                "$line0/$line1/$line2/$line3"
            }

    return new.joinToString("/")
}