import kotlin.math.max

fun main() {
    val target = getTarget(readInput("input_day17").first())
    var maxY = 0
    var velocities = 0
    for (velX in 0..target.maxX){
        for (velY in target.minY..1000){
            val probe = Probe(0,0, velX,velY, target)
            val yVal = probe.move()
            if(yVal > Int.MIN_VALUE) velocities++
            if(yVal > maxY) maxY = yVal
        }
    }
    println("Maximum y-value: $maxY")
    println("Number of unique velocities: $velocities")
}
class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

private class Probe(var x:Int, var y:Int, var velX: Int, var velY: Int, val target: TargetArea){
    fun move(): Int {
        var maxY = Int.MIN_VALUE
        while(targetReachable()){
            step()
            maxY = max(maxY, y)
            if(reachedTarget()) return maxY
        }
        return Int.MIN_VALUE
    }
    fun step(){
        x += velX
        y += velY
        if(velX > 0) velX--
        velY--
    }
    fun targetReachable(): Boolean = (x <= target.maxX && y >= target.minY)
    fun reachedTarget(): Boolean = (x in target.minX..target.maxX && y in target.minY..target.maxY)
}
fun getTarget(input: String): TargetArea {
    val ranges = input.split(",").map { s -> s.split("=")[1].split("..").map { it.toInt() }}
    return TargetArea(ranges[0][0], ranges[0][1], ranges[1][0], ranges[1][1])
}