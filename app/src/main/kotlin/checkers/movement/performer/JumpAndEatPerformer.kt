package checkers.movement.performer

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.*
import boardGame.piece.Piece

object JumpAndEatPerformer: MovementPerformer {
    override fun performMovement(piecePosition: Vector, too: Vector, board: Board): Outcome<MovementResult> {
        val jumper: Piece = when (val outcome = board.getPieceInPosition(piecePosition)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val eaten: Piece = when (val outcome = board.getPieceInPosition(piecePosition+(too-piecePosition)*0.5f)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        var newBoard = board.removePiece(eaten)
        newBoard = newBoard.movePiece(jumper, too)

        val events: List<MovementEvent> = listOf(
            Move(jumper, piecePosition, too),
            Eat(eaten, piecePosition+(too-piecePosition)*0.5f, jumper)
        )

        return SuccessfulOutcome(MovementResult(1, newBoard, events))
    }
}