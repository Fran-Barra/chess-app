package boardGame.movement

import boardGame.movement.MovementStrategy

interface MovementStrategyFactory {
    fun getMovementStrategy(): MovementStrategy
}