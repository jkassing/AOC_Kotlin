fun main() {
    fun populateFish2(input: String, days: Int): Long {
        val startingPopulation = input.split(",").map { it.toInt() }.toMutableList().groupingBy { it }.eachCount()
        val fishCount = MutableList<Long>(9){ 0 }
        startingPopulation.forEach { (key,value) -> fishCount[key] = value.toLong() }
        repeat(days){
           val zeroCount = fishCount[0]
            for(i in 0..7){
                fishCount[i] = fishCount[i+1]
            }
            fishCount[6] += zeroCount
            fishCount[8] = zeroCount
        }
        return fishCount.sum()
    }
    val test = "3,4,3,1,2"
    val input = readInput("input_day06").first()
    println("PART I: ${ populateFish2(input, 80) } is the fish population after 80 days")
    println("PART II: ${ populateFish2(input, 256) } is the fish population after 256 days")
}