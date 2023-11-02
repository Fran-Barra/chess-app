package boardGame.movement

import boardGame.movement.MovementStrategy

interface GameMovementsFactory {
    fun getMovementsStrategies(): Map<Int, MovementStrategy>
}