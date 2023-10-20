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
}