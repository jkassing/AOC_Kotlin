fun main() {
    fun populateFish(input: String, days: Int): Int {
        val fishes = input.split(",").map { it.toInt() }.toMutableList()
        val fishCount = fishes.groupingBy { it }.eachCount()
        println(fishCount)
        for(day in 0 until days){
            val newFishCount = fishes.count { it == 0 }
            fishes.replaceAll { if(it==0) 6 else it - 1 }
            fishes.addAll(List(newFishCount) {8})
        }
        return fishes.size
    }
    fun populateFish2(input: String, days: Int): Long {
        val startingPopulation = input.split(",").map { it.toInt() }.toMutableList().groupingBy { it }.eachCount()
        val fishCount = MutableList<Long>(9){ 0 }
        for((key, value ) in startingPopulation){
            fishCount[key] = value.toLong()
        }
        for(day in 0 until days){
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