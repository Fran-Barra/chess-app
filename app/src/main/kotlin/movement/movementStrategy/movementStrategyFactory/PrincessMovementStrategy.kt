package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.DistanceLimitMovement
import movement.unionMovement.OrUnionMovement

object PrincessMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        OrUnionMovement(listOf(DistanceLimitMovement(5), QueenMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}