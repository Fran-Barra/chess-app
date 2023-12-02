package boardGame.movement.movementManager

import boardGame.game.Game
import boardGame.movement.MovementResult

//TODO: verify this logic and that a new Controller is not needed
class BaseMovementManagerController(val managerUpdaters: List<UpdateMovementManagerOverEvent>):
    MovementManagerController {
    override fun updateMovementManager(
        manager: MovementManager, event: MovementResult, oldGameState: Game
    ): MovementManager {
        var managerUpdated: MovementManager = manager
        for (managerUpdater in managerUpdaters) {
            managerUpdated = managerUpdater.update(managerUpdated, event, oldGameState)
        }
        return managerUpdated
    }
}

interface UpdateMovementManagerOverEvent {
    fun update(manager: MovementManager, event: MovementResult, newGameState: Game): MovementManager
}