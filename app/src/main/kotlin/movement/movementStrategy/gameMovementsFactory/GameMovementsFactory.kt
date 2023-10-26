package movement.movementStrategy.gameMovementsFactory

import movement.MovementStrategy

interface GameMovementsFactory {
    fun getMovementsStrategies(): Map<Int, MovementStrategy>
}