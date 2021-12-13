import java.util.*

fun getValidSteps(path: List<String>, connections: Map<String, List<String>>): List<String>? =
    connections[path.last()]?.filter { it !in path || it[0].isUpperCase() }

fun getValidStepsTwice(path: List<String>, connections: Map<String, List<String>>): List<String>? =
    connections[path.last()]?.filter { cave -> cave !in path || cave[0].isUpperCase() ||
            path.none { it[0].isLowerCase() && Collections.frequency(path, it) == 2 }}

fun main() {
    fun findPaths(input: List<String>, getValidSteps: (List<String>, Map<String, List<String>>) -> List<String>?): Int {
        // get connections
        val connections = mutableMapOf<String, List<String>>().withDefault { emptyList() }
        input.forEach {
            val (key, value) = it.split("-")
            connections[key] = connections.getValue(key).plus(value)
            if(key != "start") connections[value] = connections.getValue(value).plus(key)
        }
        // find all valid paths
        val allPaths = mutableListOf(listOf("start"))
        val solutionPaths = mutableListOf<List<String>>()
        while(allPaths.isNotEmpty()){
            val currentPath = allPaths.removeFirst()
            getValidSteps(currentPath, connections)!!.forEach {
                if(it == "end") solutionPaths.add(currentPath + it)
                else allPaths.add(currentPath + it)
            }
        }
        return solutionPaths.size
    }
    val input = readInput("input_day12")
    println("PART I: ${findPaths(input, ::getValidSteps)}")
    println("PART 2: ${findPaths(input, ::getValidStepsTwice)}")
}
