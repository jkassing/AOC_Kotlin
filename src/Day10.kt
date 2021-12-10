private val bracketMap = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
private val pointMap = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)
private val autoCompletePointMap = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)

private fun errorScore(token: Char): Long = pointMap[token]!!
private fun autoCompleteScore(incomplete: List<Char>): Long =
    incomplete.foldRight(0L){ c, total -> total * 5 + autoCompletePointMap[c]!! }

fun main() {
    fun part1(input: List<String>): Long = input.sumOf { parseLine(it, errorScore = ::errorScore) }

    fun part2(input: List<String>): Long {
        val totalPoints = input.map { parseLine(it, autoComplete = ::autoCompleteScore) }.filter { it > 0 }.sorted()
        return totalPoints[totalPoints.size/2]
    }
    val input = readInput("input_day10")
    println("PART I: ${part1(input)}")
    println("PART 2: ${part2(input)}")
}
fun parseLine(input: String, errorScore: (Char) -> Long = {0}, autoComplete: (List<Char>) -> Long = {0} ): Long{
    val openList = mutableListOf<Char>()
    input.forEach { char ->
        if(char in bracketMap.values){
            if(bracketMap[openList.removeLast()] != char) return errorScore(char) // get points of wrong closing token
        }
        else openList.add(char) // add open bracket
    }
    return autoComplete(openList) // autocompletes and returns score
}

