package boardGame.movement

interface GameMovementsFactory {
    fun getMovementsStrategies(): Map<Int, MovementValidator>
}