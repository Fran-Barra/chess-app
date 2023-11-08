package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementValidator
import boardGame.movement.MovementStrategyFactory
import boardGame.movement.unionMovement.AndUnionMovementValidator
import boardGame.movement.unionMovement.OrUnionMovementValidator
import chess.movementStrategy.validators.*

//TODO: consider making the factories actual strategies
object PawnMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        val con1 = AndUnionMovementValidator(listOf(CheckVerticalPositive, VerticalMovement, DontEatInDestini))
        val con2 = AndUnionMovementValidator(listOf(DiagonalMovement, EatInDestini))
        AndUnionMovementValidator(listOf(OrUnionMovementValidator(listOf(con1, con2)), DistanceSmallerThanX(2)))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}