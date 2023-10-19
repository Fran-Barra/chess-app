package vector

class Vector (val x: Int, val y: Int){
    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y);
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector) return false
        if (x != other.x) return false
        if (y != other.y) return false
        return true
    }
}