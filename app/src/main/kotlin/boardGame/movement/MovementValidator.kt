package boardGame.movement

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

interface MovementValidator {
    fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
                 board: Board
    ): Boolean
}