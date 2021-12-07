fun main() {
    fun findVentFields(input: List<String> , diagonalAllowed: Boolean): Int {
        val fields = mutableMapOf<Pair<Int,Int>, Int>()
        for(line in input){
            val vents = line.split("->").map { string -> string.filterNot { it.isWhitespace() }.split(",").map { it.toInt() } }
            var start = Pair(vents[0][0], vents[0][1])
            val dest = Pair(vents[1][0], vents[1][1])
            if(start.first != dest.first && start.second != dest.second && !diagonalAllowed) continue
            fields[start] = fields.getOrDefault(start, 0) + 1
            while(start != dest){
                val stepX = if(start.first - dest.first < 0) 1 else if(start.first - dest.first > 0) -1 else 0
                val stepY = if(start.second - dest.second < 0) 1 else if(start.second - dest.second > 0) -1 else 0
                val field = Pair(start.first + stepX, start.second + stepY)
                fields[field] = fields.getOrDefault(field, 0) + 1
                start = field
            }
        }
        return fields.filter { it.value > 1 }.size
    }
    val input = readInput("input_day05")
    println("PART I: ${ findVentFields(input, false) } dangerous vent fields found!")
    println("PART II: ${ findVentFields(input, true) } dangerous vent fields found!")
}
