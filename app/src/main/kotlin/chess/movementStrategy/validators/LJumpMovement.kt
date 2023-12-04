package chess.movementStrategy.validators

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player
import kotlin.math.abs

object LJumpMovement: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        if (!destinationAndActualExist(game.getBoard(), actual, destination)) return false

        if (abs(actual.x - destination.x) == 1) {
            if (abs(actual.y - destination.y) != 2) return false
        }else if (abs(actual.x - destination.x) == 2) {
            if (abs(actual.y - destination.y) != 1) return false
        }else return false
        return true
    }

}