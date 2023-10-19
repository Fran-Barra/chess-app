package movement

interface UnionMovement: MovementStrategy {
    fun addStrategies(strategies: Iterable<MovementStrategy>): UnionMovement
}