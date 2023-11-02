package boardGame.game

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.player.Player
import chess.game.ChessGame

interface Game {
    fun getActualPlayer(): Player
    fun getBoard(): Board
    fun makeMovement(player: Player, origin: Vector, destination: Vector): GameMovementResult
}

sealed interface GameMovementResult
data class PlayerWon(val player: Player): GameMovementResult
data class MovementSuccessful(val newGameState: ChessGame): GameMovementResult
data class MovementFailed(val message: String): GameMovementResult