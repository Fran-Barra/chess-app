package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategy
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.DistanceLimitMovement
import boardGame.movement.unionMovement.AndUnionMovement

object KingMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(DistanceLimitMovement(2), QueenMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}