package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import movement.movementStrategy.commonLoggics.canEat
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

object EmptyOrEatEnemyInDestiny: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector,
                               board: Board): Boolean {
        if (board.getPieceInPosition(destination).isFailure) return true
        return canEat(actual, destination, board, pieceEatingRuler)
    }
}