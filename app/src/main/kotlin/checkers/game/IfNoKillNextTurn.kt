package checkers.game

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.Eat
import boardGame.movement.MovementEvent
import boardGame.movement.MovementResult
import boardGame.piece.Piece
import boardGame.player.Player
import boardGame.turnsController.NextTurnChecker
import checkers.isPieceAbleToEat

class IfNoKillNextTurn: NextTurnChecker {
    override fun goToNextTurn(movementResult: MovementResult, actualPlayer: Player): Boolean {
        val eatEvent = when (val outcome = getEatEvent(movementResult)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return true
        }

        val eaterPos = when (val outcome = findPiecePos(eatEvent.eater, movementResult.newBoard)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return true
        }
        return !isPieceAbleToEat(eaterPos, movementResult.newBoard, actualPlayer)
    }

    private fun findPiecePos(piece: Piece, board: Board): Outcome<Vector> {
        for ((xPiece: Piece, position: Vector) in board.getPiecesAndPosition()) {
            if (xPiece == piece) return SuccessfulOutcome(position)
        }
        return FailedOutcome("Position of piece not found")
    }

    private fun getEatEvent(movementResult: MovementResult): Outcome<Eat> {
        for (movementEvent in movementResult.movementEvents)
            if (movementEvent is Eat) return SuccessfulOutcome(movementEvent)
        return FailedOutcome("No eat event found")
    }
}