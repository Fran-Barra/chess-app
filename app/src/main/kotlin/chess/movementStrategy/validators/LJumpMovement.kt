package chess.movementStrategy.validators

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import kotlin.math.abs

object LJumpMovement: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                          destination: Vector, board: Board
    ): Boolean {
        if (!destinationAndActualExist(board, actual, destination)) return false

        if (abs(actual.x - destination.x) == 1) {
            if (abs(actual.y - destination.y) != 2) return false
        }else if (abs(actual.x - destination.x) == 2) {
            if (abs(actual.y - destination.y) != 1) return false
        }else return false
        return true
    }

}