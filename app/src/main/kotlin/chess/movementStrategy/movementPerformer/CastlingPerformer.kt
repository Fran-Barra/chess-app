package chess.movementStrategy.movementPerformer

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.distance
import boardGame.movement.Move
import boardGame.movement.MovementEvent
import boardGame.movement.MovementPerformer
import boardGame.movement.MovementResult
import boardGame.piece.Piece

object CastlingPerformer: MovementPerformer {
    override fun performMovement(piecePosition: Vector, too: Vector, board: Board): Outcome<MovementResult> {
        val king: Piece = when (val outcome = board.getPieceInPosition(piecePosition)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val rooks = findPiecesOfType(4, board)
        if (rooks.isEmpty()) return FailedOutcome("No rook found for castling")
        val rook: Pair<Piece, Vector> = if (rooks.size == 1) {rooks[0]} else {selectClosestToPos(rooks, too)}

        val xDir: Int = if (piecePosition.x > too.x) {-1} else {1}

        val rookDestiny = piecePosition + Vector(xDir, 0)
        var newBoard: Board = board.movePiece(rook.first, rookDestiny)

        val kingDestiny = piecePosition+ Vector(xDir*2, 0)
        newBoard = newBoard.movePiece(king, kingDestiny)

        val events: List<MovementEvent> = listOf(
            Move(king, piecePosition, kingDestiny),
            Move(rook.first, rook.second, rookDestiny)
        )
        return SuccessfulOutcome(MovementResult(1, newBoard, events))
    }

    private fun selectClosestToPos(options: List<Pair<Piece, Vector>>, pos: Vector): Pair<Piece, Vector> {
        var closest = options[0]
        for (option in options) {
            if (distance(option.second, pos) >= distance(closest.second, pos)) continue
            closest = option
        }
        return closest
    }

    private fun findPiecesOfType(type: Int, board: Board): List<Pair<Piece, Vector>> {
        val pieces: MutableList<Pair<Piece, Vector>> = mutableListOf()
        for ((piece: Piece, pos: Vector) in board.getPiecesAndPosition()) {
            if (piece.getPieceType() != type) continue
            pieces.add(Pair(piece, pos))
        }
        return pieces
    }
}