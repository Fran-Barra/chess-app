package boardGame.game

import Outcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.MovementManagerController
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.turnsController.TurnsController
import boardGame.winningConditionStrategy.WinningConditionStrategy

interface Game {
    fun getActualPlayer(): Outcome<Player>
    fun getBoard(): Board
    fun getMovementManager(): MovementManager
    fun getMovementManagerController(): MovementManagerController
    fun getPieceEatingRuler(): PieceEatingRuler
    fun getTurnController(): TurnsController
    fun getWinningConditions(): WinningConditionStrategy
    fun makeMovement(player: Player, origin: Vector, destination: Vector): GameMovementResult
}

sealed interface GameMovementResult
data class PlayerWon(val player: Player): GameMovementResult
data class MovementSuccessful(val newGameState: Game): GameMovementResult
data class MovementFailed(val message: String): GameMovementResult