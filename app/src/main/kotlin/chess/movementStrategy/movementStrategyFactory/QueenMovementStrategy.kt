package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementValidator
import boardGame.movement.MovementStrategyFactory
import boardGame.movement.unionMovement.OrUnionMovementValidator

//TODO: consider making the factories actual strategies
object QueenMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementValidator by lazy {
        OrUnionMovementValidator(listOf(BishopMovementStrategy.getMovementStrategy(),
            RookMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementValidator {
        return strategy
    }
}