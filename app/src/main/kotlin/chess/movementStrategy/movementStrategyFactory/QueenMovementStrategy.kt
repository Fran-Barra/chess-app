package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategy
import boardGame.movement.MovementStrategyFactory
import boardGame.movement.unionMovement.OrUnionMovement

//TODO: consider making the factories actual strategies
object QueenMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        OrUnionMovement(listOf(BishopMovementStrategy.getMovementStrategy(),
            RookMovementStrategy.getMovementStrategy()))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}