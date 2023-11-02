package chess.movementStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import boardGame.distance
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class DistanceLimitMovement(val maxDistance: Int): MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board
    ): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false

        if (distance(actual, destination).toInt() > maxDistance) return false
        return true
    }
}