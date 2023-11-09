package checkers.movement.validator

import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object PieceInMiddle: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        return board.getPieceInPosition(actual + (actual - destination) * 0.5f) is SuccessfulOutcome
    }
}