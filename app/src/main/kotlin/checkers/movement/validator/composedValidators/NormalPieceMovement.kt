package checkers.movement.validator.composedValidators

import boardGame.movement.MovementStrategyFactory
import boardGame.movement.MovementValidator
import boardGame.movement.unionMovement.AndUnionMovementValidator
import checkers.movement.validator.ValidateAdvancing

object NormalPieceMovement: MovementStrategyFactory {
    private val strategy by lazy {
        AndUnionMovementValidator(listOf(
            ValidateAdvancing, CheckerMovement.getMovementStrategy()
        ))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}