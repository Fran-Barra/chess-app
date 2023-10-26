package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.EmptyOrEatEnemyInDestiny
import movement.movementStrategy.LJumpMovement
import movement.unionMovement.AndUnionMovement

//TODO: consider making the factories actual strategies
object KnightMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(EmptyOrEatEnemyInDestiny, LJumpMovement))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy;
    }
}