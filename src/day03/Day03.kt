package day03

import invert
import readInput
import kotlin.math.ceil

fun main() {
    fun getMaxes(input: List<String>, invert: Boolean): String {
        val minThreshold = (input.size / 2) + 1
        var total = input[0].toCharArray().map { it.toString().toInt() }

        for (i in 1 until input.size) {
            total = total.zip(input[i].toCharArray().map { it.toString().toInt() }, Int::plus)
        }
        val maxes = total.map {
            if (it == ceil(input.size.toDouble() / 2).toInt()) {
                1
            } else {
                if (it >= minThreshold) 1 else 0
            }
        }.joinToString("")
        return if (invert) maxes.invert() else maxes
    }

    fun recursiveFilter(input: List<String>, maxes: String, loop: Int = 0, invert: Boolean): List<String> {
        val filter = maxes.elementAt(loop).toString()
        val filteredInput = input.filter {
            it.elementAt(loop).toString() == filter
        }
        return if (filteredInput.size == 1) {
            filteredInput
        } else {
            recursiveFilter(filteredInput, getMaxes(filteredInput, invert), loop + 1, invert)
        }
    }

    fun part2(input: List<String>): Int {
        val minThreshold = (input.size / 2) + 1
        var total = input[0].toCharArray().map { it.toString().toInt() }

        for (i in 1 until input.size) {
            total = total.zip(input[i].toCharArray().map { it.toString().toInt() }, Int::plus)
        }

        val maxes = total.mapIndexed { index, element ->
            if (element >= input.size / 2 && element < minThreshold) {
                1
            } else {
                if (element >= minThreshold) 1 else 0
            }
        }.joinToString("")

        val oxygen = recursiveFilter(
                input = input,
                maxes = maxes,
                invert = false)
                .first()
                .toInt(2)

        val co2 = recursiveFilter(
                input = input,
                maxes = maxes.invert(),
                invert = true)
                .first()
                .toInt(2)

        println("oxygen $oxygen")
        println("co2 $co2")

        return oxygen * co2
    }

    fun part1(input: List<String>): Int {
        val minThreshold = (input.size / 2) + 1
        var total = input[0].toCharArray().map { it.toString().toInt() }

        for (i in 1 until input.size) {
            total = total.zip(input[i].toCharArray().map { it.toString().toInt() }, Int::plus)
        }

        val maxes = total.map {
            if (it >= minThreshold) 1 else 0
        }.joinToString("")

        val gamma = maxes.toInt(2)

        val epsilon = total.map {
            if (it >= minThreshold) 0 else 1
        }.joinToString("").toInt(2)

        return gamma * epsilon
    }

    val entries = readInput("day03/Day03")
    println(part1(entries))
    println(part2(entries))
}
