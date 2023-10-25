package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

object CheckVerticalPositive: MovementStrategy {

    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (player.getPlayerId() == 0) return destination.y < actual.y
        return destination.y > actual.y
    }
}