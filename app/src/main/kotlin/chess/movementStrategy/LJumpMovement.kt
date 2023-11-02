package chess.movementStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import kotlin.math.abs

object LJumpMovement: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board
    ): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false

        if (abs(actual.x - destination.x) == 1) {
            if (abs(actual.y - destination.y) != 2) return false
        }else if (abs(actual.x - destination.x) == 2) {
            if (abs(actual.y - destination.y) != 1) return false
        }else return false
        return true
    }
}