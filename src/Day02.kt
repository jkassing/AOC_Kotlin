fun main() {
    fun part1(commands: List<String> ): Int {
        var depth = 0; var pos = 0
        for(command in commands) {
            val direction = command.split(" ")[0]
            val amount = command.split(" ")[1]
            when(direction){
                "forward" -> pos += amount.toInt()
                "up" -> depth -= amount.toInt()
                "down" -> depth += amount.toInt()
            }
        }
        return depth * pos
    }

    fun part2(commands: List<String> ): Int {
        var depth = 0; var pos = 0; var aim = 0
        for(command in commands) {
            val direction = command.split(" ")[0]
            val amount = command.split(" ")[1]
            when(direction){
                "forward" -> {
                    pos += amount.toInt()
                    depth += aim * amount.toInt()
                }
                "up" -> aim -= amount.toInt()
                "down" -> aim += amount.toInt()
            }
        }
        return depth * pos
    }

    val commands = readInput("input_day02")
    println("PART I: ${ part1(commands) }")
    println("PART 2: ${ part2(commands) }")
}
