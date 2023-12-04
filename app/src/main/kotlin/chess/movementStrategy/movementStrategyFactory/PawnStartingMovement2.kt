package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategyFactory
import boardGame.movement.MovementValidator
import boardGame.movement.unionMovement.AndUnionMovementValidator
import chess.movementStrategy.validators.*

object PawnStartingMovement2: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        val con1 = AndUnionMovementValidator(listOf(CheckVerticalPositive, VerticalMovement, DontEatInDestini))
        AndUnionMovementValidator(listOf(con1, DistanceLimitMovement(2)))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}