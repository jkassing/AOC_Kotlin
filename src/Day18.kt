import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    val input = readInput("input_day18")
    fun part1(input: List<String>): Int =
        input.map { generateSnailFishNumber(it) }.reduce{ a, b -> a.plus(b)}.magnitude()

    fun part2(input: List<String>): Int {
        val allOperations = input.mapIndexed { index, left ->
            input.drop(index+1).map { right ->
                listOf(
                    (generateSnailFishNumber(left) to generateSnailFishNumber(right)),
                    (generateSnailFishNumber(right) to generateSnailFishNumber(left))
                )
            }.flatten()
        }.flatten()
        return allOperations.maxOf { (it.first.plus(it.second)).magnitude() }
    }
    println(part1(input))
    println(part2(input))
}

fun generateSnailFishNumber(input: String): SnailfishNumber {
    val stack = mutableListOf<SnailfishNumber>()
    input.forEach { char ->
        when{
            char.isDigit() -> stack.add(RegularNumber(char.digitToInt()))
            char == ']' -> {
                val right = stack.removeLast()
                val left = stack.removeLast()
                stack.add(PairNumber(left, right))
            }
        }
    }
    return stack.removeFirst()
}

abstract class SnailfishNumber {
    var parent: SnailfishNumber? = null
    abstract fun magnitude(): Int
    abstract fun regularNumbers(): List<RegularNumber>
    abstract fun pairsWithDepth(depth: Int = 0): List<PairNumberDepth>
    abstract fun split(): Boolean
    private fun root(): SnailfishNumber = if(parent == null) this else parent!!.root()

    fun plus(otherNumber: SnailfishNumber): SnailfishNumber = PairNumber(this, otherNumber).apply { solve() }

    fun solve(){
        var unsolved = true
        while (unsolved){
            unsolved = explode() || split()
        }
    }

    private fun explode(): Boolean {
        val explodingPair = root().pairsWithDepth().firstOrNull{ it.depth >= 4}?.pair
        if(explodingPair != null){
            val regulars = root().regularNumbers()
            regulars.getOrNull(regulars.indexOf(explodingPair.left)-1)
                ?.addNumber(explodingPair.left as RegularNumber)
            regulars.getOrNull(regulars.indexOf(explodingPair.right)+1)
                ?.addNumber(explodingPair.right as RegularNumber)
            (explodingPair.parent as PairNumber).handleExplosion(explodingPair)
            return true
        }
        return false
    }
}

class RegularNumber(var value: Int): SnailfishNumber() {
    override fun magnitude(): Int = value
    override fun regularNumbers(): List<RegularNumber> = listOf(this)
    override fun pairsWithDepth(depth: Int): List<PairNumberDepth> = emptyList()
    override fun split(): Boolean = false

    fun addNumber(number: RegularNumber){
        this.value += number.value
    }

    fun splitIntoPair(parent: SnailfishNumber) =
        PairNumber(
            RegularNumber(floor(value.toDouble() / 2.0).toInt()),
            RegularNumber(ceil(value.toDouble() / 2.0).toInt())
        ).apply { this.parent = parent }
}

data class PairNumberDepth(val depth: Int, val pair: PairNumber)

class PairNumber(var left: SnailfishNumber, var right: SnailfishNumber): SnailfishNumber(){
    init {
        left.parent = this
        right.parent = this
    }
    override fun magnitude(): Int = 3*left.magnitude() + 2*right.magnitude()

    override fun regularNumbers(): List<RegularNumber> =
        this.left.regularNumbers() + this.right.regularNumbers()

    override fun pairsWithDepth(depth: Int): List<PairNumberDepth> =
        this.left.pairsWithDepth(depth + 1) + listOf(PairNumberDepth(depth, this)) +
                this.right.pairsWithDepth(depth + 1)

    override fun split(): Boolean {
        if(left is RegularNumber){
            val leftRegular = left as RegularNumber
            if(leftRegular.value >= 10){
                left = leftRegular.splitIntoPair(this)
                return true
            }
        }
        if(left.split()) return true
        if(right is RegularNumber){
            val rightRegular = right as RegularNumber
            if(rightRegular.value >= 10){
                right = rightRegular.splitIntoPair(this)
                return true
            }
        }
        return right.split()
    }

    fun handleExplosion(exploded: PairNumber){
        if(left == exploded) left = RegularNumber(0).apply { parent = this }
        else right = RegularNumber(0).apply { parent = this }
    }
}