package checkers.movement.validator.composedValidators

import boardGame.movement.MovementStrategyFactory
import boardGame.movement.MovementValidator
import boardGame.movement.unionMovement.AndUnionMovementValidator
import checkers.movement.validator.Diagonal2Slots
import checkers.movement.validator.EmptyDestination
import checkers.movement.validator.EatPieceInMiddle

object EatCheckerMovement: MovementStrategyFactory {
    private val strategy by lazy {
        AndUnionMovementValidator(listOf(Diagonal2Slots, EmptyDestination, EatPieceInMiddle))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}