package chess.movementStrategy.validators

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player
import chess.movementStrategy.commonLoggics.canEat

class EatSpecificPieceOnPassant(private val idOfPiece: Int): MovementValidator {
    //TODO: use unionMovement, not this method
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        val piece = when (val outcome = game.getBoard().getPieceInPosition(actual)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        val pieceToEatPos = Vector(destination.x, actual.y)
        val pieceToEat = when (val outcome = game.getBoard().getPieceInPosition(pieceToEatPos)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }
        if (pieceToEat.getPieceId() != idOfPiece) return false

        if (!player.playerControlColor(piece.getPieceColor())) return false

        if (!DiagonalMovement.validate(player, actual, destination, game)) return false
        if (!DistanceLimitMovement(1).validate(player, actual, destination, game)) return false
        if (!CheckVerticalPositive.validate(player, actual, destination, game)) return false

        if (!canEat(actual, pieceToEatPos, game.getBoard(), game.getPieceEatingRuler())) return false
        if (!DontEatInDestini.validate(player, actual, destination, game)) return false

        return true
    }

    fun equals(other: EatSpecificPieceOnPassant): Boolean = idOfPiece == other.idOfPiece
}