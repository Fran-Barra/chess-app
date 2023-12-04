package checkers.movement.validator

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player
import checkers.isPlayerAbleToEat

/**
 * This validator only works with standard rules of checkers since it uses isPlayerAbleToEat
 */
object NoPieceCanEat: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        return !isPlayerAbleToEat(player, game.getBoard())
    }
}