package day02

import getDirectiveValue
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var horizon = 0
        var depth = 0
        for (i in input.indices) {
            with(input[i]) {
                val value = this.getDirectiveValue()
                when {
                    contains("forward") -> horizon += value
                    contains("down") -> depth += value
                    else -> depth -= value
                }
            }
        }
        return horizon * depth
    }

    fun part2(input: List<String>): Int {
        var horizon = 0
        var depth = 0
        var aim = 0
        for (i in input.indices) {
            with(input[i]) {
                val value = this.getDirectiveValue()
                when {
                    contains("forward") -> {
                        horizon += value
                        depth += aim * value
                    }
                    contains("down") -> {
                        aim += value
                    }
                    else -> {
                        aim -= value
                    }
                }
            }
        }
        return horizon * depth
    }

    //val entries = readInput("day02/Day02_test")
    val entries = readInput("day02/Day02")
    println(part1(entries))
    println(part2(entries))
}
