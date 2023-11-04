package chess.movementStrategy.gameMovementsFactory

import boardGame.movement.GameMovementsFactory
import boardGame.movement.MovementValidator
import chess.movementStrategy.movementStrategyFactory.*

object BasicChessMovements: GameMovementsFactory {
    override fun getMovementsStrategies(): Map<Int, MovementValidator> {
        val movementStrategies: MutableMap<Int, MovementValidator> = mutableMapOf()
        movementStrategies[0] = KingMovementStrategy.getMovementStrategy()
        movementStrategies[1] = QueenMovementStrategy.getMovementStrategy()
        movementStrategies[2] = BishopMovementStrategy.getMovementStrategy()
        movementStrategies[3] = KnightMovementStrategy.getMovementStrategy()
        movementStrategies[4] = RookMovementStrategy.getMovementStrategy()
        movementStrategies[5] = PawnMovementStrategy.getMovementStrategy()
        return movementStrategies
    }
}