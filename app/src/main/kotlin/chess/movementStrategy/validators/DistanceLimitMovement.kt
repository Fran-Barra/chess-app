package chess.movementStrategy.validators

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.distance
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class DistanceLimitMovement(val maxDistance: Int): MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                          destination: Vector, board: Board
    ): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false

        if (distance(actual, destination).toInt() > maxDistance) return false
        return true
    }
}