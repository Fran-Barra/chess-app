package checkers.movement.validator

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EatPieceInMiddle: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        val eater: Piece = when (val outcome = game.getBoard().getPieceInPosition(actual)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        val toEatPiece: Piece = when (val outcome = game.getBoard().getPieceInPosition(actual + (destination-actual) * 0.5f)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        return game.getPieceEatingRuler().canPieceEatPiece(eater, toEatPiece)
    }
}