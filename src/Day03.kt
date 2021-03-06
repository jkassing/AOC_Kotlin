fun main() {
    fun part1(input: List<String> ): Int {
        var gamma = String()
        var epsilon = String()
        for(pos in 0 until  input[0].length){
            val charFrequencyAtPos = input.groupingBy { it[pos] }.eachCount()
            gamma += charFrequencyAtPos.maxByOrNull{ it.value }?.key
            epsilon += charFrequencyAtPos.minByOrNull { it.value }?.key
        }
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String> ): Int {
        var o2Rating = input
        var co2Rating = input
        for(pos in 0 until  input[0].length){
            val mostCommonBit = o2Rating.groupingBy { it[pos] }.eachCount().toSortedMap(Comparator.reverseOrder()).maxByOrNull{ it.value }?.key?: throw Exception()
            val leastCommonBit = co2Rating.groupingBy { it[pos] }.eachCount().toSortedMap().minByOrNull{ it.value }?.key ?: throw Exception()
            o2Rating = o2Rating.filter { it[pos] == mostCommonBit }
            co2Rating = co2Rating.filter { it[pos] == leastCommonBit }
        }
        return o2Rating.first().toInt() * co2Rating.first().toInt()
    }

    val binaryNumbers = readInput("input_day03")
    println("PART I: ${ part1(binaryNumbers) } power consumption")
    println("PART II: ${ part2(binaryNumbers) } life support rating!")
}

