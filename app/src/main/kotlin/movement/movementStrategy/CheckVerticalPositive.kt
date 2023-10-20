package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

class CheckVerticalPositive: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (!isVertical(actual, destination)) return false
        return destination.y > actual.y
    }

    private fun isVertical(origin: Vector, destini: Vector): Boolean{
        if (origin.x != destini.x) return false
        if (origin.y == destini.y) return false
        return true
    }
}