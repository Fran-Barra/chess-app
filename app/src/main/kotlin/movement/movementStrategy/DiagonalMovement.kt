package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector
import kotlin.math.abs

class DiagonalMovement: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false
        if (abs(actual.x - destination.x) != abs(actual.y - destination.y)) return false

        val xMul = actual.x - destination.x/ abs(actual.x - destination.x)
        val yMul = actual.y - destination.y/ abs(actual.y - destination.y)
        for (i in actual.y+1..destination.y){
            if (board.getPieceInPosition(Vector(i*xMul, i*yMul)).isSuccess)
                return false
        }
        return true
    }
}