package day04

import readInput

class Logger(
        private val isEnabled: Boolean
) {
    fun log(message: String) {
        if (isEnabled) {
            println(message)
        }
    }
}

data class BoardValue(
        val xPosition: Int,
        val yPosition: Int,
        val value: Int,
        var isMarked: Boolean = false
)

class BoardLine(
        val values: List<BoardValue>
) {
    fun isWining() = values.all { it.isMarked }

    fun sumOfAllUnmarked(): Int {
        return values.filter {
            !it.isMarked
        }.sumOf {
            it.value
        }
    }
}

class Board(
        val rowValues: List<BoardLine>
) {
    var columValues = mutableListOf<BoardLine>()

    init {
        for (y in 0..4) {
            val listOfColumValues = mutableListOf<BoardValue>()
            rowValues.forEachIndexed { x, rowLine ->
                val boardValue = rowLine.values[y]
                listOfColumValues.add(
                        boardValue
                )
            }
            columValues.add(BoardLine(listOfColumValues))
        }
    }

    private fun checkWiningLine() = rowValues.any { it.isWining() }
    private fun checkWiningRow() = columValues.any {
        it.isWining()
    }

    fun markAndCheck(value: Int): Boolean {
        val boardValue = rowValues.filter { rowLine ->
            rowLine.values.any { boardValue ->
                boardValue.value == value
            }
        }.map { rowLine ->
            rowLine.values.find { boardValue ->
                boardValue.value == value
            }
        }.firstOrNull()
        if (boardValue != null) {
            boardValue.isMarked = true
            return checkWiningLine() || checkWiningRow()
        }
        return false
    }

    fun sumOfAllUnmarked(): Int {
        return rowValues.sumOf {
            it.sumOfAllUnmarked()
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        rowValues.forEach { line ->
            line.values.forEach { value ->
                val markFlag = if (value.isMarked) "*" else ""
                builder.append("${value.value}$markFlag ")
            }
            builder.append("\n")
        }
        return builder.toString()
    }
}

val logger = Logger(true)

fun getNumbers(numbers: String): List<Int> {
    val list = numbers.split(",").map { it.toInt() }
    logger.log("We have ${list.size} numbers to draw : $list")
    return list
}

fun buildBoards(input: List<String>): List<Board> {
    val boards = input.windowed(6, 6)
            .map {
                val rowLines = it.filter { rowLine ->
                    rowLine.isNotBlank()
                }.mapIndexed { yIndex, rowLine ->
                    val boardValues = mutableListOf<BoardValue>()
                    rowLine.split(" ")
                            .filter { value -> value.isNotBlank() }
                            .forEachIndexed { xIndex, value ->
                                boardValues.add(BoardValue(xIndex, yIndex, value.toInt()))
                            }
                    BoardLine(boardValues)
                }
                Board(rowLines)
            }
    logger.log("${boards.size} boards built")
    return boards
}

fun play(number: Int, board: Board): Board? {
    var winingBoard: Board? = null
    val isWining = board.markAndCheck(number)
    if (isWining) {
        winingBoard = board
        logger.log("We have a winner with the number $number")
    }
    return winingBoard
}

fun draw(number: Int, boards: List<Board>): Board? {
    var winingBoard: Board? = null
    for (y in boards.indices) {
        winingBoard = play(number, boards[y])
        if (winingBoard != null) {
            logger.log("The wining board is the board number $y")
            logger.log("$winingBoard")
            break
        }
    }
    return winingBoard
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = getNumbers(input[0])
        val boards = buildBoards(input.subList(1, input.size))
        var winingBoard: Board? = null
        var winingNumber = 0
        for (i in numbers.indices) {
            winingNumber = numbers[i]
            winingBoard = draw(winingNumber, boards)
            if (winingBoard != null) {
                logger.log("End of the game")
                return winingNumber * winingBoard.sumOfAllUnmarked()
            }
        }

        return -1
    }


    val entries = readInput("day04/Day04")
    println(part1(entries))
}
