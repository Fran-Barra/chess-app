package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementValidator
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.DistanceLimitMovement
import boardGame.movement.unionMovement.OrUnionMovementValidator

object PrincessMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        OrUnionMovementValidator(listOf(DistanceLimitMovement(5), QueenMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}