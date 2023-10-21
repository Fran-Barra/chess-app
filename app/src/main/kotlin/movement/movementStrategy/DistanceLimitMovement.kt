package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import myMath.distance
import myMath.pow
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector
import kotlin.math.sqrt

class DistanceLimitMovement(val maxDistance: Int): MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false

        if (distance(actual, destination).toInt() > maxDistance) return false
        return true
    }
}