package boardGame.movement.movementManager

import boardGame.game.Game
import boardGame.movement.MovementResult


interface MovementManagerController {
    /**
     * Activate or deactivate movements in MovementManager given the event.
     */
    //TODO: also return a new MovementManagerController
    fun updateMovementManager(manager: MovementManager, event: MovementResult, oldGameState: Game): MovementManager
}