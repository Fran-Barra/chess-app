package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

class DistanceSmallerThanX(val x: Int): MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        return distance(actual, destination) < x
    }

    private fun distance(vec1: Vector, vec2: Vector): Float{
        TODO("Implement this method")
    }
}