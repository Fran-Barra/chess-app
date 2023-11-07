package boardGame.movement

import boardGame.game.Game


interface MovementManagerController {
    /**
     * Activate or deactivate movements in MovementManager given the event.
     */
    fun updateMovementManager(manager: MovementManager, event: MovementEvent, newGameState: Game): MovementManager
}