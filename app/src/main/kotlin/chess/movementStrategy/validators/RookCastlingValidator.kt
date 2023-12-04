package chess.movementStrategy.validators

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.piece.Piece
import boardGame.player.Player

/**
 * If it exists then it means that the rook didn't move. The actual and destinations are inverted, in relation to the real movement,
 * since it is used by the Castling validator (the king should be at destiny).
 */
object RookCastlingValidator: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        val king: Piece = when (val outcome = game.getBoard().getPieceInPosition(destination)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }
        if (king.getPieceType() != 0) return false
        if (!player.playerControlColor(king.getPieceColor())) return false

        val rook: Piece = when (val outcome = game.getBoard().getPieceInPosition(destination)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }
        if (!player.playerControlColor(rook.getPieceColor())) return false
        return true
    }
}