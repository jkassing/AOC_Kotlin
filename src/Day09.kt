fun main() {
    fun part1(input: List<String>, getBasins: Boolean = false): Int {
        var riskLevel = 0
        val lowPoints = mutableListOf<Pair<Int, Int>>()
        val allBasins = mutableListOf<Int>()

        fun getHeight(i: Int, j: Int, default: Int = 9): Int =
            input.elementAtOrNull(i)?.elementAtOrNull(j)?.toString()?.toInt() ?: default

        fun checkSurrounding(i: Int, j: Int): Set<Pair<Int, Int>> {
            val validFields = mutableSetOf<Pair<Int, Int>>()
            listOf(Pair(i, j - 1), Pair(i, j + 1), Pair(i - 1, j), Pair(i + 1, j)).map {
                if (getHeight(it.first, it.second, 0) > getHeight(i, j) &&
                    (getHeight(it.first, it.second, 0) < 9)) validFields.add(it)
            }
            return validFields
        }

        // PART I
        input.mapIndexed { i, s ->
            s.mapIndexed { j, c ->
                if (c.toString().toInt() < getHeight(i, j - 1) && c.toString().toInt() < getHeight(i, j + 1)
                    && c.toString().toInt() < getHeight(i - 1, j) && c.toString().toInt() < getHeight(i + 1, j)) {
                    riskLevel += 1 + c.toString().toInt()
                    lowPoints.add(Pair(i, j))
                }
            }
        }
        if (!getBasins) return riskLevel

        // PART II
        lowPoints.map { field ->
            val basins = mutableSetOf(field)
            var validSurrounding = checkSurrounding(field.first, field.second)
            while (validSurrounding.isNotEmpty()) {
                basins.addAll(validSurrounding)
                validSurrounding = validSurrounding.map { checkSurrounding(it.first, it.second) }.flatten().toSet()
            }
            allBasins.add(basins.size)
        }
        return allBasins.sortedDescending().take(3).toMutableList().reduce { acc, i -> acc * i }
    }
    val input = readInput("input_day09")
    println("PART I: ${part1(input)}")
    println("PART 2: ${part1(input, true)}")
}



