package boardGame.movement

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece

class FromTooMovementPerformer: MovementPerformer {
    override fun performMovement(piecePosition: Vector, too: Vector, board: Board): Outcome<MovementResult> {
        val pieceToMove: Piece = when (val outcome = board.getPieceInPosition(piecePosition)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val eventsList: MutableList<MovementEvent> = mutableListOf()
        when (val outcome = board.getPieceInPosition(too)) {
            is SuccessfulOutcome -> eventsList.add(Eat(outcome.data, too, pieceToMove))
            is FailedOutcome -> false
        }
        val newBoard: Board = board.movePiece(pieceToMove, too)
        eventsList.add(Move(pieceToMove, piecePosition, too))

        return SuccessfulOutcome(MovementResult(0, newBoard, eventsList))
    }

}