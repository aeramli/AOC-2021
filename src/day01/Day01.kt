package day01

import readInput

fun main() {
    fun part1(input: List<Int>): Int {
        var result = 0

        for(i in 1 until input.size){
            if(input[i] > input[i-1]) result++
        }
        return result
    }

    fun part2(input: List<Int>): Int {
        val flatMeasurements = mutableListOf<Int>()
        for(i in 0 until (input.size-2)){
            flatMeasurements.add(input[i] + input[i+1] + input[i+2])
        }
        return part1(flatMeasurements)
    }

    val entries = readInput("day01/Day01").map { it.toInt() }
    println(part1(entries))
    println(part2(entries))
}
