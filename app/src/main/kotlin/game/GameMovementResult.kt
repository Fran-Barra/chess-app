package game

import player.Player

sealed interface GameMovementResult
data class PlayerWon(val player: Player): GameMovementResult
data class MovementSuccessful(val newGameState: Game): GameMovementResult
data class MovementFailed(val message: String): GameMovementResult