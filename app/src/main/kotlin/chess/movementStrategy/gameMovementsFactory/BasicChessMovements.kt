package chess.movementStrategy.gameMovementsFactory

import boardGame.movement.*
import chess.movementStrategy.movementStrategyFactory.*

object BasicChessMovements: GameMovementsFactory {
    override fun getMovementsManager(): MovementManager {
        val movementStrategies: MutableMap<Int, List<Movement>> = mutableMapOf()
        movementStrategies[0] = listOf(Movement(KingMovementStrategy.getMovementStrategy(), FromTooMovementPerformer()))
        movementStrategies[1] = listOf(Movement(QueenMovementStrategy.getMovementStrategy(), FromTooMovementPerformer()))
        movementStrategies[2] = listOf(Movement(BishopMovementStrategy.getMovementStrategy(), FromTooMovementPerformer()))
        movementStrategies[3] = listOf(Movement(KnightMovementStrategy.getMovementStrategy(), FromTooMovementPerformer()))
        movementStrategies[4] = listOf(Movement(RookMovementStrategy.getMovementStrategy(), FromTooMovementPerformer()))
        movementStrategies[5] = listOf(Movement(PawnMovementStrategy.getMovementStrategy(), FromTooMovementPerformer()))
        return PieceTypeMovementManager(movementStrategies)
    }

    override fun getMovementsManagerController(): MovementManagerController {
        TODO("Not yet implemented")
    }
}