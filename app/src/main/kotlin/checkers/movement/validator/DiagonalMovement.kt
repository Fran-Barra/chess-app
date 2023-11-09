package checkers.movement.validator

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import kotlin.math.abs

object DiagonalMovement: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
        board: Board
    ): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false
        return abs(actual.x - destination.x) == abs(actual.y - destination.y)
    }
}