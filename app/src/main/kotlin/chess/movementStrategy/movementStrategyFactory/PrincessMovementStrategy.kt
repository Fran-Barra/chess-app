package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementValidator
import boardGame.movement.MovementStrategyFactory
import boardGame.movement.unionMovement.AndUnionMovementValidator
import chess.movementStrategy.validators.DistanceLimitMovement

object PrincessMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        AndUnionMovementValidator(listOf(DistanceLimitMovement(3), QueenMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}