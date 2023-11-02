package chess.movementStrategy.commonLoggics

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler

/**
 * Returns true if there is a piece in destini and is validated the eating by eating ruler.
 * Otherwise, return false.
 * If piece in origin don't exist an error might be thrown.
 */
fun canEat(origin: Vector, destini: Vector, board: Board, eatingRuler: PieceEatingRuler): Boolean{
    val pieceInDestination: Result<Piece> = board.getPieceInPosition(destini)
    if (pieceInDestination.isFailure) return false
    return eatingRuler.canPieceEatPiece(board.getPieceInPosition(origin).getOrThrow(),
        pieceInDestination.getOrThrow())
}