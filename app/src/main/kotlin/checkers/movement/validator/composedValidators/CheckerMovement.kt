package checkers.movement.validator.composedValidators

import boardGame.movement.MovementStrategyFactory
import boardGame.movement.MovementValidator
import boardGame.movement.unionMovement.AndUnionMovementValidator
import checkers.movement.validator.*

object CheckerMovement: MovementStrategyFactory {
    private val strategy by lazy {
        AndUnionMovementValidator(listOf(
            DistanceLimitMovement(1), DiagonalMovement, EmptyDestination, NoPieceCanEat
        ))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}