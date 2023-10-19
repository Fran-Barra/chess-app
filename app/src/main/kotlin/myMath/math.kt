package myMath

public fun pow(base: Int, pow: Int): Int {
    if (base == 0) return 0
    if (base == 1) return 1
    return base * pow(base, pow-1)
}