package movement.unionMovement

import board.Board
import movement.MovementStrategy
import movement.UnionMovement
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

class AndUnionMovement(private val strategies: Iterable<MovementStrategy>): UnionMovement {

    override fun addStrategies(strategies: Iterable<MovementStrategy>): UnionMovement {
        return AndUnionMovement(strategies.union(strategies))
    }

    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
        destination: Vector, board: Board): Boolean {
        for (movementStrategy in strategies)
            if (!movementStrategy.checkMovement(pieceEatingRuler, player, actual, destination, board))
                return false
        return true;
    }
}