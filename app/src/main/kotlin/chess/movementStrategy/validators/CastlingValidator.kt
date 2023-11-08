package chess.movementStrategy.validators

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import kotlin.math.abs

object CastlingValidator: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        val king: Piece = when (val outcome = board.getPieceInPosition(actual)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }
        if (king.getPieceType() != 0) return false

        val rook: Piece = when (val outcome = board.getPieceInPosition(destination)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        if (actual.y != destination.y) return false

        if (rook.getPieceType() != 4) return false

        if (!isPathEmpty(actual, destination, board)) return false

        if (isPathInCheck()) return false
        return true
    }

    private fun isPathEmpty(from: Vector, too: Vector, board: Board): Boolean {
        val xDir = if (from.x > too.x) {-1} else {1}

        for (i in 1..abs(from.x-too.x))
            if (board.getPieceInPosition(Vector(from.x + i * xDir, from.y)) is SuccessfulOutcome)
                return false
        return true
    }

    //TODO: implement this
    private fun isPathInCheck() = false
}