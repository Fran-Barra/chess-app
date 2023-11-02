package boardGame

import boardGame.board.Vector
import kotlin.math.sqrt

fun pow(base: Int, pow: Int): Int {
    if (base == 0 || base == 1) return base
    if (pow == 0) return 1
    if (pow == 1) return base
    return base * pow(base, pow-1)
}

fun distance(vec1: Vector, vec2: Vector): Double{
    val dif: Vector = vec1 - vec2
    return sqrt((pow(dif.x, 2) + pow(dif.y, 2)).toDouble())
}