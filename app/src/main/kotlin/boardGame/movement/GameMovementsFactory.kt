package boardGame.movement

import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.MovementManagerController

interface GameMovementsFactory {
    fun getMovementsManager(): MovementManager
    fun getMovementsManagerController(): MovementManagerController
}