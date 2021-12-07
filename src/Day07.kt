import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>, fuel: (Int, Int) -> Int): Int? =
        (0.rangeTo(input.maxOf { it })).minOfOrNull { pos -> input.sumOf { fuel(pos, it) } }

    val test = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }
    val input = readInput("input_day07").first().split(",").map { it.toInt() }
    println("PART I: ${part1(input) { x: Int, y: Int -> abs(x - y) }} minimum fuel required")
    println("PART II: ${part1(input) { x: Int, y: Int -> (abs(x - y) * (abs(x - y)+1)) / 2 }} minimum fuel required")
}

