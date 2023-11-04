package boardGame.movement.unionMovement

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.movement.UnionMovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class OrUnionMovementValidator(private val strategies: Iterable<MovementValidator>): UnionMovementValidator {
    override fun addStrategies(strategies: Iterable<MovementValidator>): UnionMovementValidator {
        return AndUnionMovementValidator(strategies.union(strategies))
    }

    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board
    ): Boolean {
        for (movementStrategy in strategies)
            if (movementStrategy.checkMovement(pieceEatingRuler, player, actual, destination, board))
                return true
        return false
    }
}