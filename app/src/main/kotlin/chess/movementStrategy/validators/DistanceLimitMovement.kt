package chess.movementStrategy.validators

import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.distance
import boardGame.game.Game
import boardGame.player.Player

class DistanceLimitMovement(val maxDistance: Int): MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        if (!destinationAndActualExist(game.getBoard(), actual, destination)) return false

        if (distance(actual, destination).toInt() > maxDistance) return false
        return true
    }
}