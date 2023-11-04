package boardGame.movement

interface MovementStrategyFactory {
    fun getMovementStrategy(): MovementValidator
}