package chess.movementStrategy.movementPerformer

import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementPerformer
import boardGame.movement.MovementResult

/**
 * Is useful for cases such as RookCastlingValidator
 */
object EmptyPerformer: MovementPerformer {
    override fun performMovement(piecePosition: Vector, too: Vector, board: Board): Outcome<MovementResult> {
        return SuccessfulOutcome(MovementResult(-1, board, listOf()))
    }
}