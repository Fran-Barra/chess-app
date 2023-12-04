package boardGame.movement.unionMovement

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.movement.UnionMovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class OrUnionMovementValidator(private val strategies: Iterable<MovementValidator>): UnionMovementValidator {
    override fun addStrategies(strategies: Iterable<MovementValidator>): UnionMovementValidator {
        return AndUnionMovementValidator(strategies.union(strategies))
    }

    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        for (movementStrategy in strategies)
            if (movementStrategy.validate(player, actual, destination, game))
                return true
        return false
    }
}