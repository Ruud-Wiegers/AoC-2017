package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution
import nl.ruudwiegers.adventofcode.y2017.TuringState.*

fun main(args: Array<String>) {
    print(Day25.solvePartOne(""))
}

object Day25 : AdventSolution(2017, 25, "The Halting Problem") {

    override fun solvePartOne(input: String): String {

        val tape = mutableMapOf<Int, Boolean>()
        var position = 0
        var state: TuringState = A
        repeat(12861455) {
            val transition = if (tape[position] == true) transitionsOne[state]!! else transitionsZero[state]!!
            tape[position] = transition.writeOne
            if (transition.toRight) position++ else position--
            state = transition.nextState
        }

        return tape.values.count { it }.toString()
    }

    override fun solvePartTwo(input: String): String {
        return "Free Star!"
    }

}


enum class TuringState {
    A, B, C, D, E, F
}

class Transition(val writeOne: Boolean, val toRight: Boolean, val nextState: TuringState)
val transitionsZero = mapOf(
        A to Transition(true, true, B),
        B to Transition(true, false, C),
        C to Transition(true, true, E),
        D to Transition(true, false, A),
        E to Transition(false, true, A),
        F to Transition(true, true, E)
)

val transitionsOne = mapOf(
        A to Transition(false, false, B),
        B to Transition(false, true, E),
        C to Transition(false, false, D),
        D to Transition(true, false, A),
        E to Transition(false, true, F),
        F to Transition(true, true, A)
)



