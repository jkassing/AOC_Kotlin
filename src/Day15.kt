import java.util.*

fun main() {
    val input = readInput("input_day15").map { s -> s.map { it.toString().toInt() } }
    println("PART I: ${findLowestRisk(input)}")
    println("PART II: ${findLowestRisk(bigInput(input))}")
}

private fun findLowestRisk(input: List<List<Int>>): Int{
    val riskLevel = mutableMapOf<Pair<Int, Int>, Int>()
    val pq = PriorityQueue<Field>(compareBy { it.priority })
    val start = (0 to 0); val end = (input.size-1 to input.size-1)
    riskLevel[start] = 0
    pq.offer(Field(start, 0))

    // Dijkstra
    while (pq.isNotEmpty()){
        val field = pq.poll().pos
        if(field == end) break
        field.getNeighbors(input.size).map {
            val newRisk = riskLevel[field]!! + input[it.first][it.second]
            if(it !in riskLevel || newRisk < riskLevel.getValue(it)){
                val newField = Field(it, newRisk)
                riskLevel[it] = newRisk
                if(newField in pq) pq.remove(newField)
                pq.offer(newField)
            } }
    }
    return riskLevel[end] ?: throw Exception("no viable path found from start to end!")
}

private class Field(val pos: Pair<Int, Int>, var priority: Int)

private fun Pair<Int, Int>.getNeighbors(max: Int): List<Pair<Int, Int>>{
    val neighbors = mutableListOf<Pair<Int, Int>>()
    listOf((0 to 1), (1 to 0), (-1 to 0), (0 to -1)).map {
        if(this.first + it.first in 0 until max && this.second + it.second in 0 until max){
            neighbors.add((this.first + it.first to this.second + it.second))
        }
    }
    return neighbors
}

private fun bigInput(input: List<List<Int>>): List<List<Int>>{
    var newList = input.toMutableList()
    fun getCost(cost:Int, i: Int) : Int{
        val newCost = (cost + i + 1) % 9
        return if(newCost == 0) 9 else newCost
    }
    repeat(4){ i ->
        newList = MutableList(newList.size) {
                row -> newList[row].plus(input[row].map { getCost(it, i) }) }
    }
    repeat(4){i ->
        repeat(input.size){ row ->
            newList.addAll(listOf(newList[row].map { getCost(it, i) }))
        }
    }
    return newList
}