package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategy
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.*
import boardGame.movement.unionMovement.AndUnionMovement
import boardGame.movement.unionMovement.OrUnionMovement

//TODO: consider making the factories actual strategies
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