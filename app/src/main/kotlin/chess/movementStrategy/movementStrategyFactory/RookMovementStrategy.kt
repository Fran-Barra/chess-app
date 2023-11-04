package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementValidator
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.EmptyOrEatEnemyInDestiny
import chess.movementStrategy.HorizontalMovement
import chess.movementStrategy.VerticalMovement
import boardGame.movement.unionMovement.AndUnionMovementValidator
import boardGame.movement.unionMovement.OrUnionMovementValidator

//TODO: consider making the factories actual strategies
object RookMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        AndUnionMovementValidator(listOf(EmptyOrEatEnemyInDestiny, OrUnionMovementValidator(listOf(HorizontalMovement, VerticalMovement))))
    }

    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}