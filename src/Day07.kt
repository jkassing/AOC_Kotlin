import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>): Int? {
        return (0.rangeTo(input.maxOf { it })).minOfOrNull { pos -> input.sumOf { abs(pos - it) } }
    }
    fun part2(input: List<Int>): Int? {
        return (0.rangeTo(input.maxOf { it })).minOfOrNull { pos -> input.sumOf { (0..abs(pos - it)).sum() } }
    }
    val test = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }
    val input = readInput("input_day07").first().split(",").map { it.toInt() }
    println("PART I: ${part1(input)} minimum fuel required")
    println("PART II: ${part2(input)} minimum fuel required")
}

