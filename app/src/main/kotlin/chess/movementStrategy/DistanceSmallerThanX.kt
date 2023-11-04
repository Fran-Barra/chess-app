package chess.movementStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pow
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import kotlin.math.sqrt

class DistanceSmallerThanX(val x: Int): MovementValidator {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board
    ): Boolean {
        return distance(actual, destination) < x
    }

    private fun distance(vec1: Vector, vec2: Vector): Float{
        return sqrt((pow(vec1.x - vec2.x, 2) + pow(vec1.y - vec2.y, 2)).toFloat())
    }
}