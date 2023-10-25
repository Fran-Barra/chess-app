package movement.movementStrategy.movementStrategyFactory

import movement.MovementStrategy
import movement.movementStrategy.*
import movement.unionMovement.AndUnionMovement
import movement.unionMovement.OrUnionMovement

object PawnMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        val con1 = AndUnionMovement(listOf(CheckVerticalPositive, VerticalMovement, DontEatInDestini))
        val con2 = AndUnionMovement(listOf(DiagonalMovement, EatInDestini))
        AndUnionMovement(listOf(OrUnionMovement(listOf(con1, con2)), DistanceSmallerThanX(2)))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}