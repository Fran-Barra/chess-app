package boardGame.movement


interface MovementManagerController {
    /**
     * Activate or deactivate movements in MovementManager given the event.
     */
    fun updateMovementManager(manager: MovementManager, event: MovementEvent): MovementManager
}