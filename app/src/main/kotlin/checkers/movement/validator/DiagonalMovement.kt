package checkers.movement.validator

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player
import kotlin.math.abs

object DiagonalMovement: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        if (!destinationAndActualExist(game.getBoard(), actual, destination)) return false
        return abs(actual.x - destination.x) == abs(actual.y - destination.y)
    }
}