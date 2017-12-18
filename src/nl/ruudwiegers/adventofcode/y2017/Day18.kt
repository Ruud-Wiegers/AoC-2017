package nl.ruudwiegers.adventofcode.y2017

import nl.ruudwiegers.adventofcode.AdventSolution

fun main(args: Array<String>) {
    Day18.solve()
}

object Day18 : AdventSolution(2017, 18) {

    override fun solvePartOne(input: String): String {
        val instructions: List<List<String>> = lex(input)

        val reg = mutableMapOf<String, Long>()
        var pc = 0
        while (pc in instructions.indices) {
            pc += execute(instructions[pc], reg)
        }
        return reg["snd"].toString()
    }

    private fun execute(op: List<String>, reg: MutableMap<String, Long>): Int {
        when (op[0]) {
            "snd" -> reg["snd"] = eval(op[1], reg)
            "set" -> reg[op[1]] = eval(op[2], reg)
            "add" -> reg[op[1]] = eval(op[1], reg) + eval(op[2], reg)
            "mul" -> reg[op[1]] = eval(op[1], reg) * eval(op[2], reg)
            "mod" -> reg[op[1]] = eval(op[1], reg) % eval(op[2], reg)
            "rcv" -> if (eval(op[1], reg) != 0L) {
                return 100000
            }
            "jgz" -> if (eval(op[1], reg) > 0) return eval(op[2], reg).toInt()
        }
        return 1
    }

    private fun eval(value: String, registers: Map<String, Long>) = value.toLongOrNull() ?: registers[value] ?: 0
    private fun lex(input: String) = input.split("\n").map { row -> row.split(" ") }


    override fun solvePartTwo(input: String): String {
        val instructions = input.split("\n").map { row -> row.split(" ") }
        val q1 = mutableListOf<Long>()
        val q2 = mutableListOf<Long>()

        val ec1 = ExecutionContext(0, instructions, q1, q2)
        val ec2 = ExecutionContext(1, instructions, q2, q1)

        while (ec1.canExecute() || ec2.canExecute()) {
            if (ec1.canExecute()) ec1.execute() else ec2.execute()
        }
        return ec2.sendCount.toString()
    }

}


class ExecutionContext(id: Long,
                       private val instructions: List<List<String>>,
                       private val sendQueue: MutableList<Long>,
                       private val receiveQueue: MutableList<Long>) {

    private val registers: MutableMap<String, Long> = mutableMapOf("p" to id)

    private var pc = 0

    var sendCount = 0

    fun canExecute() = pc in instructions.indices && (instructions[pc][0] != "rcv" || receiveQueue.isNotEmpty())

    fun execute() {
        val operator: String = instructions[pc][0]
        val x: String = instructions[pc][1]
        val y: String? = instructions[pc].getOrNull(2)

        execute(operator, x, y)
        pc++
    }

    private fun execute(operator: String, x: String, y: String?) {
        when (operator) {
            "snd" -> {
                sendQueue += eval(x)
                sendCount++
            }
            "set" -> registers[x] = eval(y)
            "add" -> registers[x] = eval(x) + eval(y)
            "mul" -> registers[x] = eval(x) * eval(y)
            "mod" -> registers[x] = eval(x) % eval(y)
            "rcv" -> registers[x] = receiveQueue.removeAt(0)
            "jgz" -> if (eval(x) > 0) {
                pc += eval(y).toInt() - 1
            }
        }
    }

    private fun eval(value: String?) = value?.toLongOrNull() ?: registers[value] ?: 0
}
