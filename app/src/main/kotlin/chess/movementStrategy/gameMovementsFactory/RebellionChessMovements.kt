package chess.movementStrategy.gameMovementsFactory

import boardGame.movement.GameMovementsFactory
import boardGame.movement.MovementStrategy
import chess.movementStrategy.movementStrategyFactory.*

object RebellionChessMovements: GameMovementsFactory {
    override fun getMovementsStrategies(): Map<Int, MovementStrategy> {
        val movementStrategies: MutableMap<Int, MovementStrategy> = mutableMapOf()
        movementStrategies[0] = KingMovementStrategy.getMovementStrategy()
        movementStrategies[1] = QueenMovementStrategy.getMovementStrategy()
        movementStrategies[2] = PrincessMovementStrategy.getMovementStrategy()
        movementStrategies[3] = KnightMovementStrategy.getMovementStrategy()
        movementStrategies[4] = RookMovementStrategy.getMovementStrategy()
        movementStrategies[5] = PawnMovementStrategy.getMovementStrategy()
        return movementStrategies
    }
}