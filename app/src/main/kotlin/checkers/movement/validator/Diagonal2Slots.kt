package checkers.movement.validator

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import kotlin.math.abs

object Diagonal2Slots: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector, board: Board
    ): Boolean {
        if (!DiagonalMovement.validate(pieceEatingRuler, player, actual, destination, board)) return false
        return abs((actual - destination).x) == 2
    }
}