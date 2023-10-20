package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import myMath.pow
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector
import kotlin.math.sqrt

class DistanceSmallerThanX(val x: Int): MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        return distance(actual, destination) < x
    }

    private fun distance(vec1: Vector, vec2: Vector): Float{
        return sqrt((pow(vec1.x - vec2.x, 2) + pow(vec1.y - vec2.y, 2)).toFloat())
    }
}