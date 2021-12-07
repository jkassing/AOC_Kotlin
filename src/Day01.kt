fun main() {
    fun part1(scanValues: List<Int>): Int = scanValues.windowed(2).count { it[0] < it[1] }

    fun part2(scanValues: List<Int>): Int = scanValues.windowed(4).count { it[0] < it[3] }

    val sonarScan = readInput("input_day01")
    val scanValues = sonarScan.map { it.toInt() }
    println("PART I: ${ part1(scanValues) } scans had higher measurements than the previous scan!")
    println("PART II: ${ part2(scanValues) } windows had higher measurements than the previous window!")
}

