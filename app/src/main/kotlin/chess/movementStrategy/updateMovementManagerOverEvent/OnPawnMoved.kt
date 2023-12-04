package chess.movementStrategy.updateMovementManagerOverEvent

import boardGame.game.Game
import boardGame.movement.FromTooMovementPerformer
import boardGame.movement.Move
import boardGame.movement.MovementResult
import boardGame.movement.movementManager.Movement
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.UpdateMovementManagerOverEvent
import chess.movementStrategy.movementStrategyFactory.PawnStartingMovement2

object OnPawnMoved: UpdateMovementManagerOverEvent {
    override fun update(manager: MovementManager, event: MovementResult, newGameState: Game): MovementManager {
        var newManager = manager
        for (movementEvent in event.movementEvents) {
            if (movementEvent !is Move) continue
            if (movementEvent.piece.getPieceType() != 5) continue
            newManager = newManager.removeMovement(
                movementEvent.piece.getPieceId(),
                Movement(PawnStartingMovement2.getMovementStrategy(), FromTooMovementPerformer)
            )
        }
        return newManager
    }
}