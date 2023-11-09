package checkers.movement.validator

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import checkers.isPlayerAbleToEat

/**
 * This validator only works with standard rules of checkers since it uses isPlayerAbleToEat
 */
object NoPieceCanEat: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        return !isPlayerAbleToEat(player, board)
    }
}