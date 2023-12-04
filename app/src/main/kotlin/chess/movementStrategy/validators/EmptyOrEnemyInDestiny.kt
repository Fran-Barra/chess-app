package chess.movementStrategy.validators

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import chess.movementStrategy.commonLoggics.canEat
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EmptyOrEatEnemyInDestiny: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        return when (game.getBoard().getPieceInPosition(destination)) {
            is SuccessfulOutcome -> canEat(actual, destination, game.getBoard(), game.getPieceEatingRuler())
            is FailedOutcome -> true
        }
    }
}