fun main() {
    fun part1(scanValues: List<Int> ): Int {
        var depthIncreased = 0
        for(scan in scanValues.windowed(2)){
            if(scan[0] < scan[1]) depthIncreased++
        }
        return depthIncreased
    }

    fun part2(scanValues: List<Int> ): Int {
        var depthIncreased = 0
        for(scan in scanValues.windowed(4)){
            // scan[1] and scan[2] irrelevant as in both sums
            if(scan[0] < scan[3]) depthIncreased++
        }
        return depthIncreased
    }

    val sonarScan = readInput("input_day01")
    val scanValues = sonarScan.map { it.toInt() }
    println("PART I: ${ part1(scanValues) } scans had higher measurements than the previous scan!")
    println("PART II: ${ part2(scanValues) } windows had higher measurements than the previous window!")
}

