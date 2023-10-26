package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.EmptyOrEatEnemyInDestiny
import movement.movementStrategy.HorizontalMovement
import movement.movementStrategy.VerticalMovement
import movement.unionMovement.AndUnionMovement
import movement.unionMovement.OrUnionMovement

//TODO: consider making the factories actual strategies
object RookMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(EmptyOrEatEnemyInDestiny, OrUnionMovement(listOf(HorizontalMovement, VerticalMovement))))
    }

    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}