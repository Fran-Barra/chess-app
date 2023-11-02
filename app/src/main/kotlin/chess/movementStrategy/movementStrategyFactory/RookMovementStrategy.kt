package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategy
import boardGame.movement.MovementStrategyFactory
import chess.movementStrategy.EmptyOrEatEnemyInDestiny
import chess.movementStrategy.HorizontalMovement
import chess.movementStrategy.VerticalMovement
import boardGame.movement.unionMovement.AndUnionMovement
import boardGame.movement.unionMovement.OrUnionMovement

//TODO: consider making the factories actual strategies
object RookMovementStrategy: MovementStrategyFactory {
    private val strategy: MovementStrategy by lazy {
        AndUnionMovement(listOf(EmptyOrEatEnemyInDestiny, OrUnionMovement(listOf(HorizontalMovement, VerticalMovement))))
    }

    override fun getMovementStrategy(): MovementStrategy {
        return strategy
    }
}