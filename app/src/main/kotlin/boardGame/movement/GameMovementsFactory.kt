package boardGame.movement

interface GameMovementsFactory {
    fun getMovementsManager(): MovementManager
    fun getMovementsManagerController(): MovementManagerController
}