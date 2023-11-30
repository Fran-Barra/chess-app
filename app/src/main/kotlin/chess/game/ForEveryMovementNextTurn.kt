package chess.game

import boardGame.movement.MovementResult
import boardGame.player.Player
import boardGame.turnsController.NextTurnChecker

class ForEveryMovementNextTurn: NextTurnChecker {
    override fun goToNextTurn(movementResult: MovementResult, actualPlayer: Player): Boolean = true
}