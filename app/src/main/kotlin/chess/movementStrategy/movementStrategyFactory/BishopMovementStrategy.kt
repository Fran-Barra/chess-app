package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementValidator
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.DiagonalMovement
import chess.movementStrategy.EmptyOrEatEnemyInDestiny
import boardGame.movement.unionMovement.AndUnionMovementValidator

//TODO: consider making the factories actual strategies
object BishopMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        AndUnionMovementValidator(listOf(EmptyOrEatEnemyInDestiny, DiagonalMovement))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}