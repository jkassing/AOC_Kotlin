fun main() {
    fun part1(input: List<String> ): Int {
        var gamma = String()
        var epsilon = String()
        for(pos in 0 until  input[0].length){
            val charFrequencyAtPos = input.groupingBy { it[pos] }.eachCount()
            gamma += charFrequencyAtPos.maxByOrNull{ it.value }?.key
            epsilon += charFrequencyAtPos.minByOrNull { it.value }?.key
        }
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
    }

    fun part2(input: List<String> ): Int {
        var o2Rating = input
        var co2Rating = input
        for(pos in 0 until  input[0].length){
            val mostCommonBit = o2Rating.groupingBy { it[pos] }.eachCount().toSortedMap(Comparator.reverseOrder()).maxByOrNull{ it.value }?.key
            val leastCommonBit = co2Rating.groupingBy { it[pos] }.eachCount().toSortedMap().minByOrNull{ it.value }?.key
            o2Rating = o2Rating.filter { it[pos] == mostCommonBit }
            co2Rating = co2Rating.filter { it[pos] == leastCommonBit }
        }
        return Integer.parseInt(o2Rating.first(), 2) * Integer.parseInt(co2Rating.first(), 2)
    }
    /*val test = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010""".trimIndent().split("\n")*/

    val binaryNumbers = readInput("input_day03")
    println("PART I: ${ part1(binaryNumbers) } power consumption")
    println("PART II: ${ part2(binaryNumbers) } life support rating!")
}

