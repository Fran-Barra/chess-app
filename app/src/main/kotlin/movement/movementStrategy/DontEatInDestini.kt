package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import movement.movementStrategy.commonLoggics.canEat
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

class DontEatInDestini: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        return !canEat(actual, destination, board, pieceEatingRuler)
    }

}