package vector

class Vector (val x: Int, val y: Int){
    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y);
    }

    operator fun minus(other: Vector): Vector {
        return Vector(x - other.x, y - other.y)
    }

    operator fun times(other: Vector): Int{
        return x*other.x + y*other.y
    }

    operator fun times(mul: Int): Vector{
        return Vector(x*mul, y*mul)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector) return false
        if (x != other.x) return false
        if (y != other.y) return false
        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}