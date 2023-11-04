package chess.movementStrategy

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import chess.movementStrategy.commonLoggics.canEat
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object EmptyOrEatEnemyInDestiny: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
                          board: Board
    ): Boolean {
        return when (board.getPieceInPosition(destination)) {
            is SuccessfulOutcome -> canEat(actual, destination, board, pieceEatingRuler)
            is FailedOutcome -> true
        }
    }
}