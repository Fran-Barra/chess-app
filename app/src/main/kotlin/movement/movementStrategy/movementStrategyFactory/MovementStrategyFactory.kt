package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy

interface MovementStrategyFactory {
    fun getMovementStrategy(): MovementStrategy
}