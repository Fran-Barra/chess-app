package boardGame.movement

interface UnionMovementValidator: MovementValidator {
    fun addStrategies(strategies: Iterable<MovementValidator>): UnionMovementValidator
}