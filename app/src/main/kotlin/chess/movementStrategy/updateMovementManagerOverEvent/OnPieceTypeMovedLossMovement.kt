package chess.movementStrategy.updateMovementManagerOverEvent

import boardGame.game.Game
import boardGame.movement.Move
import boardGame.movement.MovementResult
import boardGame.movement.movementManager.Movement
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.UpdateMovementManagerOverEvent

/**
 * If a piece of type pieceType moves then it will lose the privilege of using the movement
 */
class OnPieceTypeMovedLossMovement(
    private val pieceType: Int,
    private val movement: Movement
): UpdateMovementManagerOverEvent {
    override fun update(manager: MovementManager, event: MovementResult, newGameState: Game): MovementManager {
        var newManager = manager
        for (movementEvent in event.movementEvents) {
            if (movementEvent !is Move) continue
            if (movementEvent.piece.getPieceType() != pieceType) continue
            newManager = newManager.removeMovement(movementEvent.piece.getPieceId(), movement)
        }
        return newManager
    }
}