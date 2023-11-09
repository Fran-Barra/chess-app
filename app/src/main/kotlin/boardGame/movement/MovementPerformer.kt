package boardGame.movement

import Outcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece

interface MovementPerformer {
    fun performMovement(piecePosition: Vector, too: Vector, board: Board): Outcome<MovementResult>
}

data class MovementResult(val movementIdentifier: Int, val newBoard: Board, val movementEvents: List<MovementEvent>)
sealed interface MovementEvent
data class Move(val piece: Piece, val from: Vector, val too: Vector): MovementEvent
data class Eat(val piece: Piece, val position: Vector, val eater: Piece): MovementEvent
data class Promotion(val oldPiece: Piece, val newPiece: Piece, val position: Vector): MovementEvent