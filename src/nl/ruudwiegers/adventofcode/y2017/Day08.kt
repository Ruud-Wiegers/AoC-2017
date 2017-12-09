package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

object Day08 : AdventSolution {
    @JvmStatic
    fun main(args: Array<String>) {
        Day08.solve()
    }

    override val year = 2017
    override val day = 8

    override fun solvePartOne(input: String): String {
        val registers = mutableMapOf<String, Int>()

        for (instruction in parseInput(input)) {
            instruction.executeOn(registers)
        }

        return registers.values.max().toString()
    }

    override fun solvePartTwo(input: String): String {
        val registers = mutableMapOf<String, Int>()
        return parseInput(input)
                .onEach { it.executeOn(registers) }
                .map { registers[it.register] ?: 0 }
                .max()
                .toString()
    }


    private fun parseInput(input: String): Sequence<Instruction> {
        val regex = "([a-z]+) ([a-z]+) (-?\\d+) if ([a-z]+) ([^ ]+) (-?\\d+)"
                .toRegex()
        return input.splitToSequence("\n")
                .map { regex.matchEntire(it)?.destructured!! }
                .map { (a, b, c, d, e, f) ->
                    val sign = if (b == "inc") 1 else -1
                    Instruction(a, c.toInt() * sign, d, e, f.toInt())
                }
    }


    private data class Instruction(val register: String,
                                   private val amount: Int,
                                   private val comparandRegister: String,
                                   private val comparator: String,
                                   private val comparand: Int) {
        fun executeOn(registers: MutableMap<String, Int>) {
            if (compare(registers[comparandRegister] ?: 0)) {
                registers[register] = (registers[register] ?: 0) + amount
            }
        }

        fun compare(v: Int) = when (comparator) {
            "<" -> v < comparand
            "==" -> v == comparand
            ">" -> v > comparand
            "<=" -> v <= comparand
            ">=" -> v >= comparand
            "!=" -> v != comparand
            else -> throw IllegalStateException(comparator)
        }

    }
}