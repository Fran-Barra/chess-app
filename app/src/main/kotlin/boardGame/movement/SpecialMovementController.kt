package boardGame.movement

import Outcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

interface SpecialMovementController {
    /**
     * Given the update data the SpecialController will eliminate the special movements related with the event.
     * Considering the event relation that you established when created.
     */
    fun updateSpecialMovementController(event: GameEvent, piece: Piece) : SpecialMovementController
    fun removePiece(piece: Piece) : SpecialMovementController
    fun checkMovement(eatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                      destination: Vector, board: Board
    ) : Outcome<SpecialMovement>

}

sealed interface GameEvent
