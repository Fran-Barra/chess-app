package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector
import kotlin.math.abs

object DiagonalMovement: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false
        if (abs(actual.x - destination.x) != abs(actual.y - destination.y)) return false

        return pathIsEmpty(actual, destination, board)
    }

    private fun pathIsEmpty(origin: Vector, destiny: Vector, board: Board): Boolean {
        val xDir = if (origin.x < destiny.x) 1 else -1
        val yDir = if (origin.y < destiny.y) 1 else -1
        for (i in 1 until abs(origin.x-destiny.x))
            if (board.getPieceInPosition(Vector(i*xDir + origin.x, i*yDir + origin.y)).isSuccess)
                return false
        return true
    }
}