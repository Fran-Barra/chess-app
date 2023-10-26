package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.DiagonalMovement
import movement.movementStrategy.EmptyOrEatEnemyInDestiny
import movement.unionMovement.AndUnionMovement

//TODO: consider making the factories actual strategies
object BishopMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(EmptyOrEatEnemyInDestiny, DiagonalMovement))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}