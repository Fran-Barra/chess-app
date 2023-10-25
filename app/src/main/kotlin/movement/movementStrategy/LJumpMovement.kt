package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector
import kotlin.math.abs

object LJumpMovement: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false

        if (abs(actual.x - destination.x) == 1) {
            if (abs(actual.y - destination.y) != 2) return false
        }else if (abs(actual.x - destination.x) == 2) {
            if (abs(actual.y - destination.y) != 1) return false
        }else return false
        return true
    }
}