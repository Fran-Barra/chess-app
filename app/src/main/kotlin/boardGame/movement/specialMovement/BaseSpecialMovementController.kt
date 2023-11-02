package boardGame.movement.specialMovement

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.GameEvent
import exceptionHandler
import boardGame.movement.SpecialMovement
import boardGame.movement.SpecialMovementController
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

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
                               destination: Vector, board: Board
    ): Result<SpecialMovement> {
        val pieceR: Result<Piece> = board.getPieceInPosition(actual)
        if (pieceR.isFailure) return exceptionHandler(pieceR, "board", "get boardGame.piece in position")
        val piece: Piece = pieceR.getOrNull()!!

        if (!pieceMovements.containsKey(piece))
            return Result.failure(Exception("There are no specified movements for this boardGame.piece"))

        val pairR: List<Pair<List<GameEvent>, SpecialMovement>> = pieceMovements[piece]
            ?: return Result.failure(Exception("There are no specified movements for this boardGame.piece"))
        if (pairR.isEmpty())
            return Result.failure(Exception("There are no specified movements for this boardGame.piece"))

        throw NotImplementedError()
        //for (pair in pairR) {
        //pair.second.
        //}
    }
}