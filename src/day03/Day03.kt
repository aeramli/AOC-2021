package day03

import getDirectiveValue
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val minThreshold = (input.size / 2 ) +1
        var total = input[0].toCharArray().map { it.toString().toInt() }

        for (i in 1 until input.size) {
            total = total.zip(input[i].toCharArray().map { it.toString().toInt() }, Int::plus)
        }

        val gamma = total.map {
            if (it >= minThreshold) 1 else 0
        }.joinToString("").toInt(2)

        val epsilon = total.map {
            if (it >= minThreshold) 0 else 1
        }.joinToString("").toInt(2)

        return gamma * epsilon
    }

    val entries = readInput("day03/Day03")
    println(part1(entries))
}
