import kotlin.math.pow

fun main() {
    println(Long.MAX_VALUE)
    println(winningCombinationsP1)
    println(winningCombinationsP2)
    println(nbrOfUniversesP1())
    println(nbrOfUniversesP2())
}

val validDiceRolls = listOf(3,4,5,6,7,8,9)
val variations = mapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)
var winningCombinationsP1 = genWinningCombinations(7, 10)
var winningCombinationsP2 = genWinningCombinations(7, 6)

fun genWinningCombinations(winningScore: Int, start: Int): MutableMap<Int, Int> {
    val winningMap: MutableMap<Int, Int> = HashMap()
    fun genCombsRec(position: Int, turnCount: Int, currScore: Int, numCombinations: Int){
        if(currScore >= winningScore)
            winningMap[turnCount] = winningMap.getOrDefault(turnCount, 0) + numCombinations
        else{
            for(rolls in validDiceRolls){
                val points = ((position + rolls) % 10).let { if(it  == 0) 10 else it }
                genCombsRec(points, turnCount + 1, currScore + points, numCombinations * variations[rolls]!!)
            }
        }
    }
    genCombsRec(start, 0, 0, 1)
    return winningMap
}

fun getLosingCombinations(winningOtherPlayer: MutableMap<Int, Int>, turn: Int): Long {
    val fastest = winningOtherPlayer.minOf { it.key }
    fun notWonUntilKnow(recTurn: Int): Long {
        return if (recTurn < fastest) 27.0.pow(turn).toLong()
        else if (recTurn == fastest) winningOtherPlayer[fastest]!!.toLong()
        else notWonUntilKnow(recTurn - 1) * 27 - winningOtherPlayer[recTurn]!!
    }
    return notWonUntilKnow(turn)
}

/**
 * Universen P1 gewinnt =
 *  für alle t in  turns wo p1 gewinnt
 * Sum of all t  winningP1[t] * getLosingCombinations(winningP2[t-1]]
 */

fun nbrOfUniversesP2(): Long =
    winningCombinationsP2.entries.sumOf {
        it.value * getLosingCombinations(winningCombinationsP1, it.key)
    }

fun nbrOfUniversesP1(): Long =
    winningCombinationsP1.entries.sumOf {
        it.value * getLosingCombinations(winningCombinationsP2, it.key-1)
    }
