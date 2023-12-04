package checkers.movement.validator

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player
import kotlin.math.abs

object Diagonal2Slots: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        if (!DiagonalMovement.validate(player, actual, destination, game)) return false
        return abs((actual - destination).x) == 2
    }
}