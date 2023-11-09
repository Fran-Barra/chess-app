package checkers.movement.validator

import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EmptyDestination: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        return board.getPieceInPosition(destination) !is SuccessfulOutcome
    }
}