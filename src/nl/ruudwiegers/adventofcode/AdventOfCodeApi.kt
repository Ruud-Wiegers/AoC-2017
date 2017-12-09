package nl.ruudwiegers.adventofcode

import java.net.URL

object AdventOfCodeApi {


    fun retrieveInput(day: Int, year: Int): String {
        require(year in 2015..2100) { "$year is not a valid year. AoC started in 2015" }
        require(day in 1..25) { "$day is not a valid day in december. Choose a day in 1-25" }
        return URL("http://adventofcode.com/$year/day/$day/input")
                .openConnection()
                .apply { addRequestProperty("Cookie", "session=" + AOC_SESSION) }
                .getInputStream().use { it.readBytes() }
                .toString(Charsets.UTF_8)
                .trimEnd()
    }

}