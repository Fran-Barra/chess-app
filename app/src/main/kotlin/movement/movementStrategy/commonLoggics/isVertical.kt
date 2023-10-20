package movement.movementStrategy.commonLoggics

import vector.Vector

fun isVertical(origin: Vector, destini: Vector): Boolean{
    if (origin.x != destini.x) return false
    if (origin.y == destini.y) return false
    return true
}