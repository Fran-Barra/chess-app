package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.HorizontalMovement
import movement.movementStrategy.VerticalMovement
import movement.unionMovement.OrUnionMovement

object RookMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        OrUnionMovement(listOf(HorizontalMovement, VerticalMovement))
    }

    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}