package movement

import board.Board
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

interface MovementStrategy {
    fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
                      board: Board): Boolean
}