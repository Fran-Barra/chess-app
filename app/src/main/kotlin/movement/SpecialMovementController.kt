package movement

import board.Board
import event.GameEvent
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

interface SpecialMovementController {
    /**
     * Given the update data the SpecialController will eliminate the special movements related with the event.
     * Considering the event relation that you established when created.
     */
    fun updateSpecialMovementController(event: GameEvent, piece: Piece) : SpecialMovementController
    fun removePiece(piece: Piece) : SpecialMovementController
    fun checkMovement(eatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                      destination: Vector, board: Board
    ) : Result<SpecialMovement>

}