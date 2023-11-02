package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategy
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.DistanceLimitMovement
import boardGame.movement.unionMovement.OrUnionMovement

object PrincessMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        OrUnionMovement(listOf(DistanceLimitMovement(5), QueenMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}