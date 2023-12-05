package chess.movementStrategy.movementPerformer

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.Eat
import boardGame.movement.Move
import boardGame.movement.MovementPerformer
import boardGame.movement.MovementResult
import kotlin.math.abs

object EnPassant: MovementPerformer {
    override fun performMovement(piecePosition: Vector, too: Vector, board: Board): Outcome<MovementResult> {
        if (abs(piecePosition.x - too.x) != 1) return FailedOutcome("No correct vectors for this movement")
        if (abs(piecePosition.y - too.y) != 1) return FailedOutcome("No correct vectors for this movement")

        val pieceToEat = when (val outcome = board.getPieceInPosition(Vector(too.x, piecePosition.y))) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome("No piece to eat, movement was not valid")
        }

        var newBoard = board.removePiece(pieceToEat)

        val pieceToMove = when (val outcome = newBoard.getPieceInPosition(piecePosition)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome("Piece to move does not exists in $piecePosition")
        }
        newBoard = newBoard.movePiece(pieceToMove, too)

        val events = listOf(
            Move(pieceToMove, piecePosition, too),
            Eat(pieceToEat, Vector(piecePosition.x, too.y), pieceToMove)
        )

        return SuccessfulOutcome(MovementResult(3, newBoard, events))
    }
}