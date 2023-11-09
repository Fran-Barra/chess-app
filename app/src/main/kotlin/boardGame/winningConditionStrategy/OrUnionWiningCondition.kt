package boardGame.winningConditionStrategy

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.game.Game

class OrUnionWiningCondition(private val wcs: Iterable<WinningConditionStrategy>): WinningConditionStrategy {
    override fun checkWinningConditions(game: Game): Outcome<Boolean> {
        for (wc in wcs)
            when (val outcome = wc.checkWinningConditions(game)) {
                is SuccessfulOutcome -> if (outcome.data) return SuccessfulOutcome(true) else continue
                is FailedOutcome -> return outcome
            }
        return SuccessfulOutcome(false)
    }
}