package chess.movementStrategy.validators

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.piece.Piece
import boardGame.player.Player
import kotlin.math.abs

object CastlingValidator: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        val king: Piece = when (val outcome = game.getBoard().getPieceInPosition(actual)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }
        if (king.getPieceType() != 0) return false

        val rook: Piece = when (val outcome = game.getBoard().getPieceInPosition(destination)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        if (actual.y != destination.y) return false

        if (rook.getPieceType() != 4) return false

        //TODO: check that it's the corresponding movement validator
        if (game.getMovementManager().findValidMovementPerformer(player, destination, actual, game) is FailedOutcome)
            return false

        if (!isPathEmpty(actual, destination, game.getBoard())) return false

        if (isPathInCheck()) return false
        return true
    }

    private fun isPathEmpty(from: Vector, too: Vector, board: Board): Boolean {
        val xDir = if (from.x > too.x) {-1} else {1}

        for (i in 1 until abs(from.x-too.x))
            if (board.getPieceInPosition(Vector(from.x + i * xDir, from.y)) is SuccessfulOutcome)
                return false
        return true
    }

    //TODO: implement this
    private fun isPathInCheck() = false
}