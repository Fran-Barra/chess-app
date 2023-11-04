package boardGame.movement

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class PieceTypeMovementManager(private val pieceTypeMovements: Map<Int, List<Movement>>): MovementManager {
    override fun findValidMovementPerformer(
        pieceEatingRuler: PieceEatingRuler,
        player: Player,
        actual: Vector,
        destination: Vector,
        board: Board
    ): Outcome<MovementPerformer> {
        val pieceType: Int = when (val outcome = board.getPieceInPosition(actual)) {
            is SuccessfulOutcome -> outcome.data.getPieceType()
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val pieceTypeMovements: List<Movement> = pieceTypeMovements[pieceType]?:
            return FailedOutcome("This piece type don't have movements")

        if (pieceTypeMovements.isEmpty())
            return FailedOutcome("This piece type don't have movements")

        for (movement in pieceTypeMovements) {
            if (!movement.validator.validate(pieceEatingRuler, player, actual, destination, board)) continue
            return SuccessfulOutcome(movement.performer)
        }
        return FailedOutcome("No valid movement found")
    }

    override fun addMovement(id: Int, movement: Movement): MovementManager {
        val newMap: MutableMap<Int, List<Movement>> = pieceTypeMovements.toMutableMap()
        val movementList = newMap[id] ?: emptyList()
        val updatedList = movementList + movement
        newMap[id] = updatedList
        return PieceTypeMovementManager(newMap)
    }

    override fun removeMovement(id: Int, movement: Movement): MovementManager {
        val newMap: MutableMap<Int, List<Movement>> = pieceTypeMovements.toMutableMap()
        val movementList = newMap[id] ?: emptyList()
        val updatedList = movementList - movement
        newMap[id] = updatedList
        return PieceTypeMovementManager(newMap)
    }
}