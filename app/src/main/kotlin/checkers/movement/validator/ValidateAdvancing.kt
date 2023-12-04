package checkers.movement.validator

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player

object ValidateAdvancing: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        if (player.getPlayerId() == 0) return destination.y < actual.y
        return destination.y > actual.y
    }
}