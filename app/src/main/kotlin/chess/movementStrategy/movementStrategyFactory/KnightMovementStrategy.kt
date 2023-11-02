package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategy
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.EmptyOrEatEnemyInDestiny
import chess.movementStrategy.LJumpMovement
import boardGame.movement.unionMovement.AndUnionMovement

//TODO: consider making the factories actual strategies
object KnightMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(EmptyOrEatEnemyInDestiny, LJumpMovement))
    }
    override fun getMovementStrategy(): MovementStrategy {
        return strategy;
    }
}