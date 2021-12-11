import java.lang.Exception

private fun getNumber(pattern: List<String>, signals: List<String>): Int {
    val four = pattern.first { it.length == 4 }.toCharArray().toSet()
    val seven = pattern.first { it.length == 3 }.toCharArray().toSet()
    var number = ""
    signals.map {
        number += when {
            it.length == 2 -> "1"
            it.length == 3 -> "7"
            it.length == 4 -> "4"
            it.length == 7 -> "8"
            it.toCharArray().intersect(four).size == 4 && it.length == 6 -> "9"
            it.toCharArray().intersect(seven).size == 3 && it.length == 6 -> "0"
            it.toCharArray().intersect(four).size == 3 && it.length == 6 -> "6"
            it.toCharArray().intersect(seven).size == 3 && it.length == 5 -> "3"
            it.toCharArray().intersect(four).size == 3 && it.length == 5 -> "5"
            it.toCharArray().intersect(four).size == 2 && it.length == 5 -> "2"
            else -> throw Exception("not a valid number")
        }
    }
    return number.toInt()
}

fun main() {
    fun part1(input: List<String>): Int = input.map { it.split(" | ")[1].split(" ") }.flatten().count {
        it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }

    fun part2(input: List<String>): Int = input.map { string -> string.split(" | ").map { it.split(" ") } }
        .map { getNumber(it[0], it[1]) }.sumOf { it }

    val input = readInput("input_day08")
    println("PART I: ${part1(input)} scans had higher measurements than the previous scan!")
    println("PART II: ${part2(input)} windows had higher measurements than the previous window!")
}

