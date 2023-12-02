package checkers.movement.validator

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.distance
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class DistanceLimitMovement(val maxDistance: Int): MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        if (!destinationAndActualExist(board, actual, destination)) return false
        return distance(actual, destination).toInt() <= maxDistance
    }
}