package checkers.movement.validator.composedValidators

import boardGame.movement.MovementStrategyFactory
import boardGame.movement.MovementValidator
import boardGame.movement.unionMovement.AndUnionMovementValidator
import checkers.movement.validator.EmptyDestination
import checkers.movement.validator.NoPieceCanEat
import checkers.movement.validator.ValidateAdvancing
import chess.movementStrategy.validators.DiagonalMovement
import chess.movementStrategy.validators.DistanceLimitMovement

object NormalPieceMovement: MovementStrategyFactory {
    private val strategy by lazy {
        AndUnionMovementValidator(listOf(
            DistanceLimitMovement(1), DiagonalMovement, EmptyDestination, ValidateAdvancing, NoPieceCanEat
        ))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}