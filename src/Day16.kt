import java.math.BigInteger

fun main() {
    val input = readInput("input_day16").first()
    val test = "D2FE28"
    println(decodeMessage(input, true))
    println(decodeMessage(input))
}

fun decodeMessage(input: String, part1: Boolean = false): Long {
    var message = hexToBits(input)
    var versionSum = 0L

    fun takeAndDrop(n: Int): String{
        val taken = message.take(n)
        message = message.drop(n)
        return taken
    }
    fun handleLengthType(): Pair<Int, Int> {
        val lengthType = Integer.parseInt(takeAndDrop(1), 2)
        return if(lengthType == 0) (0 to Integer.parseInt(takeAndDrop(15), 2))
        else (1 to Integer.parseInt(takeAndDrop(11), 2))
    }
    fun getLiteral(): Long{
        var last = false
        var literal = ""
        while(!last){
            if(takeAndDrop(1).toInt() == 0) last = true
            literal += takeAndDrop(4)
        }
        return BigInteger(literal, 2).toLong()
    }
    fun handleOperation(operation: Int, literals: List<Long>): Long{
        return when(operation){
            0 -> literals.sum()
            1 -> literals.reduce { acc, l -> acc * l }
            2 -> literals.minOf { it }
            3 -> literals.maxOf { it }
            5 -> if(literals[0] > literals[1]) 1 else 0
            6 -> if(literals[0] < literals[1]) 1 else 0
            7 -> if(literals[0] == literals[1]) 1 else 0
            else -> throw Exception("unknown operation type!")
        }
    }
    fun decodePacket(): Long{
        val literals = mutableListOf<Long>()
        versionSum += BigInteger(takeAndDrop(3), 2).toLong()
        return when(val operation = Integer.parseInt(takeAndDrop(3), 2)){
            4 -> getLiteral()
            else -> {
                val (lengthType, remaining) = handleLengthType()
                var count = 0
                while (count < remaining){
                    val lenPre = message.length
                    literals.add(decodePacket())
                    count += if(lengthType == 0) lenPre - message.length else 1
                }
                handleOperation(operation, literals)
            }
        }
    }
    val value = decodePacket()
    return if(part1) versionSum else value
}

fun hexToBits(hex: String): String {
    var bits = ""
    hex.map { bits += "%4s".format(it.toString().toInt(16).toString(2)).replace(" ", "0") }
    return bits
}
