package boardGame.movement

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

interface MovementStrategy {
    fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
                      board: Board
    ): Boolean
}