package chess.movementStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import chess.movementStrategy.commonLoggics.canEat
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EmptyOrEatEnemyInDestiny: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
                               board: Board
    ): Boolean {
        if (board.getPieceInPosition(destination).isFailure) return true
        return canEat(actual, destination, board, pieceEatingRuler)
    }
}