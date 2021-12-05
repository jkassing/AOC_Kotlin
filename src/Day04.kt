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
                    if(number.value == drawnNumber){
                        number.marked = true
                        if(board.hasWon())
                            if(lastBoard && (boards.size - boardsWon.size) > 1){
                                boardsWon.add(board)
                                break
                            }
                            else{
                                return board.numbers.filterNot { it.marked }.sumOf { it.value } * drawnNumber
                            }
                    }
                }
            }
            boards.removeAll(boardsWon)
        }
        println("no board has won the bingo :(")
        return null
    }
    val test = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7""".trimIndent().split("\n")
    val input = readInput("input_day04")
    println("PART I: ${ bingo(input, false) } is the winning score for the first board")
    println("PART II: ${ bingo(input, true) } is the winning score for the last board")
}


