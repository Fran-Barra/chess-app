package boardGame.movement.unionMovement

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import boardGame.movement.UnionMovement
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

class AndUnionMovement(private val strategies: Iterable<MovementStrategy>): UnionMovement {

    override fun addStrategies(strategies: Iterable<MovementStrategy>): UnionMovement {
        return AndUnionMovement(strategies.union(strategies))
    }

    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board
    ): Boolean {
        for (movementStrategy in strategies)
            if (!movementStrategy.checkMovement(pieceEatingRuler, player, actual, destination, board))
                return false
        return true;
    }
}