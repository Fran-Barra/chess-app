package chess.movementStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import chess.movementStrategy.commonLoggics.canEat
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EatInDestini: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                          destination: Vector, board: Board
    ): Boolean {
        return canEat(actual, destination, board, pieceEatingRuler)
    }
}