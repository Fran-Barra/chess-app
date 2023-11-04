package chess.movementStrategy.commonLoggics

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler

/**
 * Returns true if there is a piece in destini and is validated the eating by eating ruler.
 * Otherwise, return false.
 * If piece in origin don't exist also returns false
 */
fun canEat(origin: Vector, destini: Vector, board: Board, eatingRuler: PieceEatingRuler): Boolean{
    val pieceInDestination: Piece = when (val outcome = board.getPieceInPosition(destini)) {
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> return false
    }
    val pieceInOrigin: Piece = when (val outcome = board.getPieceInPosition(origin)) {
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> return false
    }
    return eatingRuler.canPieceEatPiece(pieceInOrigin, pieceInDestination)
}