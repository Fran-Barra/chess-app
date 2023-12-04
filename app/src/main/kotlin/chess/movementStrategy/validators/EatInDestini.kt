package chess.movementStrategy.validators

import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import chess.movementStrategy.commonLoggics.canEat
import boardGame.player.Player

object EatInDestini: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        return canEat(actual, destination, game.getBoard(), game.getPieceEatingRuler())
    }
}