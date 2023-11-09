package checkers.movement.validator

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EatPieceInMiddle: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        val eater: Piece = when (val outcome = board.getPieceInPosition(actual)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        val toEatPiece: Piece = when (val outcome = board.getPieceInPosition(actual + (actual - destination) * 0.5f)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        return pieceEatingRuler.canPieceEatPiece(eater, toEatPiece)
    }
}