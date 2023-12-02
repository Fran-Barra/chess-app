package boardGame.movement.movementManager

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.player.Player

class GetPieceTypeId() : GetIdStrategy {
    override fun getId(player: Player, actual: Vector, destination: Vector, board: Board): Outcome<Int> =
        when (val outcome = board.getPieceInPosition(actual)) {
            is SuccessfulOutcome -> SuccessfulOutcome(outcome.data.getPieceType())
            is FailedOutcome -> FailedOutcome(outcome.error)
        }
}

class GetPieceId() : GetIdStrategy {
    override fun getId(player: Player, actual: Vector, destination: Vector, board: Board): Outcome<Int> =
        when (val outcome = board.getPieceInPosition(actual)) {
            is SuccessfulOutcome -> SuccessfulOutcome(outcome.data.getPieceId())
            is FailedOutcome -> FailedOutcome(outcome.error)
        }
}