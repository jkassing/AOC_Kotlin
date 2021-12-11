fun main() {
    fun simulateOctopi(octoList: MutableList<MutableList<Int>>, times: Int = 100, getFirstSync: Boolean = false): Int? {
        var flashCount = 0

        fun handleFlash(x: Int, y: Int){
            octoList[x][y] = 0
            flashCount++
            listOf(Pair(x,y+1), Pair(x,y-1), Pair(x+1,y), Pair(x-1,y),
                Pair(x-1,y-1), Pair(x+1,y-1), Pair(x-1,y+1), Pair(x+1,y+1)).forEach {
                if((octoList.getOrNull(it.first)?.getOrNull(it.second) ?: -1) > 0) octoList[it.first][it.second]++
                if((octoList.getOrNull(it.first)?.getOrNull(it.second) ?: 0) > 9) handleFlash(it.first, it.second)
            }
        }

        repeat(times){step ->
            octoList.map{ s -> s.replaceAll{ it + 1 } }
            octoList.mapIndexed { i, row -> row.map { if(it > 9) handleFlash(i, row.indexOf(it)) } }
            if(octoList.sumOf { it.sum() } == 0 && getFirstSync) return step+1
        }
        return if(!getFirstSync) flashCount else null
    }
    val input = readInput("input_day11").map { s -> s.map {
        it.toString().toInt() }.toMutableList() }.toMutableList()
    val input2 = readInput("input_day11").map { s -> s.map {
        it.toString().toInt() }.toMutableList() }.toMutableList()

    println("PART I: ${simulateOctopi(input, 100)}")
    println("PART 2: ${simulateOctopi(input2, 1000, true)?: throw Exception("Try increasing steps!")}")
}
