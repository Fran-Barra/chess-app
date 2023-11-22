package boardGame.turnsController

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.player.Player

class CircleTurnController(private val circle: List<Player>, private var turn: Int): TurnsController {
    override fun getPlayers(): List<Player> = circle

    override fun getNextPlayerTurn(): Outcome<Pair<Player, TurnsController>> {
        if (circle.isEmpty()) return FailedOutcome("No players found")
        if (turn >= circle.size) turn = 0
        return SuccessfulOutcome(Pair(circle[turn], CircleTurnController(circle, turn+1)))
    }

    override fun addPlayer(player: Player): TurnsController {
        return CircleTurnController(circle.plus(player), turn)
    }
}