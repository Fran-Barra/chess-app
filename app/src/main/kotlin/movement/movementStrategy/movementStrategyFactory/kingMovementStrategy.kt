package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.DistanceLimitMovement
import movement.unionMovement.AndUnionMovement

object KingMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(DistanceLimitMovement(2), QueenMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}