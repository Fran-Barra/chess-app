package boardGame.movement

import boardGame.game.Game

class BaseMovementManagerController(val managerUpdaters: List<UpdateMovementManagerOverEvent>): MovementManagerController {
    override fun updateMovementManager(
        manager: MovementManager, event: MovementEvent, newGameState: Game
    ): MovementManager {
        var managerUpdated: MovementManager = manager
        for (managerUpdater in managerUpdaters) {
            managerUpdated = managerUpdater.update(managerUpdated, event, newGameState)
        }
        return managerUpdated
    }
}

interface UpdateMovementManagerOverEvent {
    fun update(manager: MovementManager, event: MovementEvent, newGameState: Game): MovementManager
}