package boardGame.movement

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.player.Player

interface MovementValidator {
    fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean
}