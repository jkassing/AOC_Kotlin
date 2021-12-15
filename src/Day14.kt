import java.io.File

fun main() {
    fun polymerize(input: List<String>, steps: Int): Long{
        var tuples = mutableMapOf<String, Long>().withDefault { 0L }
        parseInput(input).windowed(2).forEach{ tuples[it] = tuples[it]?.plus(1L) ?: 1L }

        repeat(steps){
            val insertions = mutableMapOf<String, Long>().withDefault { 0L }
            tuples.map { insertions[it.key] = it.value }
            tuples.forEach {
                insertions[it.key] = maxOf(insertions.getValue(it.key).minus(it.value), 0) // remove tuple
                insertions.increment(it.key[0] + rules[it.key]!!, it.value) // replace left side
                insertions.increment(rules[it.key]!! + it.key[1], it.value) // replace right side
            }
            tuples = insertions
        }
        val letterFrequency = tuples.byCharFrequency(input.first().last())
        return letterFrequency.maxOf { it.value } - letterFrequency.minOf { it.value }
    }

    val input = File("src/data/", "input_day14.txt").readText().split("\n\n")
    println("PART I: ${polymerize(input, 10)}")
    println("PART II: ${polymerize(input, 40)}")
}

val rules = mutableMapOf<String, String>()
fun parseInput(input: List<String>): String {
    val start = input.first()
    input[1].split("\n").forEach{
        if(it.isNotBlank()){
            val (pair, insertion) = it.split(" -> ")
            rules[pair] = insertion
        }
    }
    return start
}
private fun Map<String, Long>.byCharFrequency(lastChar: Char): Map<Char, Long> =
    this
        .map { it.key.first() to it.value }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() + if (it.key == lastChar) 1 else 0 }

private fun MutableMap<String, Long>.increment(key: String, value: Long) {
    this[key] = this.getValue(key) + value
}