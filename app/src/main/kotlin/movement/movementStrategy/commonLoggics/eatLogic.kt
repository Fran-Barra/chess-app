package movement.movementStrategy.commonLoggics

import board.Board
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import vector.Vector

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