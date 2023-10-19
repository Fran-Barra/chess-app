package movement.specialMovement

import board.Board
import event.GameEvent
import exceptionHandler
import movement.SpecialMovement
import movement.SpecialMovementController
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

class BaseSpecialMovementController(
    private val pieceMovements: Map<Piece, List<Pair<List<GameEvent>, SpecialMovement>>>):
    SpecialMovementController {
    override fun updateSpecialMovementController(event: GameEvent, piece: Piece): SpecialMovementController {
        TODO("Not yet implemented")
    }

    override fun removePiece(piece: Piece): SpecialMovementController {
        if (!pieceMovements.containsKey(piece)) return this

        val newMap: MutableMap<Piece, List<Pair<List<GameEvent>, SpecialMovement>>> =
            pieceMovements.toMutableMap()
        newMap.remove(piece)
        return  BaseSpecialMovementController(newMap)
    }

    override fun checkMovement(eatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Result<SpecialMovement> {
        val pieceR: Result<Piece> = board.getPieceInPosition(actual)
        if (pieceR.isFailure) return exceptionHandler(pieceR, "board", "get piece in position")
        val piece: Piece = pieceR.getOrNull()!!

        if (!pieceMovements.containsKey(piece))
            return Result.failure(Exception("There are no specified movements for this piece"))

        val pairR: List<Pair<List<GameEvent>, SpecialMovement>> = pieceMovements[piece]
            ?: return Result.failure(Exception("There are no specified movements for this piece"))
        if (pairR.isEmpty())
            return Result.failure(Exception("There are no specified movements for this piece"))

        throw NotImplementedError()
        //for (pair in pairR) {
        //pair.second.
        //}
    }
}