package boardGame.winningConditionStrategy

import Outcome
import boardGame.game.Game


interface WinningConditionStrategy {
    fun checkWinningConditions(game: Game): Outcome<Boolean>
}