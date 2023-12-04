package checkers.movement.validator

import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EmptyDestination: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        return game.getBoard().getPieceInPosition(destination) !is SuccessfulOutcome
    }
}