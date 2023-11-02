package boardGame.turnsController

import boardGame.player.Player

class CircleTurnController(private val circle: List<Player>, private var turn: Int): TurnsController {
    override fun getNextPlayerTurn(): Result<Pair<Player, TurnsController>> {
        if (circle.isEmpty()) return Result.failure(Exception("No players found"))
        if (turn >= circle.size) turn = 0
        return Result.success(Pair(circle.get(turn), CircleTurnController(circle, turn+1)))
    }

    override fun addPlayer(player: Player): TurnsController {
        return CircleTurnController(circle.plus(player), turn)
    }
}