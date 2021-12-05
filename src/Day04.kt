private class Board(val numbers: MutableList<Number>) {
    fun hasWon() = rowCompleted() || colCompleted()
    fun rowCompleted() = numbers.groupBy { it.x }.any { key -> key.value.all { it.marked } }
    fun colCompleted() = numbers.groupBy { it.y }.any { key -> key.value.all { it.marked } }
}
private class Number(val value: Int, val x: Int, val y: Int, var marked: Boolean = false)

fun main() {

    fun bingo(input: List<String>, lastBoard: Boolean): Int? {
        val drawnNumbers = input.first().split(",").map { it.toInt() }
        val boardNumbers = input.subList(2, input.size).flatMap {
                string -> string.split(" ").filterNot { it.isBlank() }.map { it.toInt() } }.toList()

        // build Boards
        val boards = mutableListOf<Board>()
        var numberIndex = 0
        for(i in 0 until (boardNumbers.size/25)){ // each board contains 25 numbers
            val numbers = mutableListOf<Number>()
            for(rowIndex in 0 until 5)
                for(colIndex in 0 until 5){
                    numbers.add(Number(boardNumbers[numberIndex], rowIndex, colIndex))
                    numberIndex ++
                }
            boards.add(Board(numbers))
        }
        // play bingo
        for(drawnNumber in drawnNumbers){
            val boardsWon = mutableListOf<Board>()
            for(board in boards){
                for(number in board.numbers){
                    if(number.value == drawnNumber) {
                        number.marked = true
                        if (boards.all { it.hasWon() } || !lastBoard && board.hasWon()) {
                            return board.numbers.filterNot { it.marked }.sumOf { it.value } * drawnNumber
                        }
                        else if(lastBoard && board.hasWon()) {
                            boardsWon.add(board)
                            break
                        }
                    }
                }
            }
            boards.removeAll(boardsWon)
        }
        println("no board has won the bingo :(")
        return null
    }

    val input = readInput("input_day04")
    println("PART I: ${ bingo(input, false) } is the winning score for the first board")
    println("PART II: ${ bingo(input, true) } is the winning score for the last board")
}


