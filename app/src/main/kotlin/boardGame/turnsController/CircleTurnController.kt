package boardGame.turnsController

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.movement.MovementResult
import boardGame.player.Player

class CircleTurnController(
    private val circle: List<Player>,
    private var turn: Int,
    private val nextTurnChecker: NextTurnChecker
): TurnsController {

    override fun getPlayers(): List<Player> = circle

    override fun getActualPlayer(): Outcome<Player> =
        if (circle.isEmpty()) {FailedOutcome("No players found")} else {SuccessfulOutcome(circle[getTurn()])}

    override fun addPlayer(player: Player): TurnsController  =
        CircleTurnController(circle.plus(player), turn, nextTurnChecker)

    override fun updateTurnController(movementResult: MovementResult): TurnsController {
        val actualPlayer = when (val outcome = getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return this
        }

        if (!nextTurnChecker.goToNextTurn(movementResult, actualPlayer)) return this
        return CircleTurnController(circle, getTurn()+1, nextTurnChecker)
    }

    private fun getTurn(): Int {
        if (turn >= circle.size) turn = 0
        return turn
    }
}

interface NextTurnChecker{
    fun goToNextTurn(movementResult: MovementResult, actualPlayer: Player): Boolean
}