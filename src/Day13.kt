import java.io.File

private val axisMap = mapOf("x" to 0, "y" to 1)
private fun loadInput(): Pair<List<MutableList<Int>>, List<String>> {
    val input = File("src/data/", "input_day13.txt").readText().split("\n\n")
    return Pair(input[0].split("\n").map { it.split(",").map { c -> c.toInt() }.toMutableList() },
        input[1].split("\n").map { it.split(" ").last() })
}

private fun visualize(dots: Set<MutableList<Int>>){
    (0 .. dots.maxOf { it[1] }).forEach {j -> (0 .. dots.maxOf { it[0] }).forEach { i ->
            if(listOf(i,j) in dots) print("#") else print(" ") }
        println("") }
}

fun main() {
    val (dots, folds) = loadInput()

    fun fold(axis: Int, value: Int) {
        dots.forEach {
            if(it[axis] > value) it[axis] -= (it[axis] - value) * 2
        }
    }

    fun origami(oneFold: Boolean = false) {
        folds.forEach {
            val (axis, value) = it.split("=")
            fold(axisMap[axis]!!, value.toInt())
            if(oneFold) println("Part I: " + dots.toSet().size).also { return }
        }
        visualize(dots.toSet())
    }
    origami(true)
    origami()
}

