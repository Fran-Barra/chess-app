package myMath

fun pow(base: Int, pow: Int): Int {
    if (base == 0 || base == 1) return base
    if (pow == 0) return 1
    if (pow == 1) return base
    return base * pow(base, pow-1)
}