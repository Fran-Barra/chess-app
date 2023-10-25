package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

object VerticalMovement: MovementStrategy {
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (!isVertical(actual, destination)) return false
        return !checkIfPieceInMiddlePath(actual, destination, board)
    }

    private fun checkIfPieceInMiddlePath(origin: Vector, destini: Vector, board: Board): Boolean{
        if (origin.y < destini.y) return checkIfPieceBetweenYMinAndYMax(origin.y, destini.y, origin.x, board)
        return checkIfPieceBetweenYMinAndYMax(destini.y, origin.y, origin.x, board)
    }

    private fun checkIfPieceBetweenYMinAndYMax(yMin: Int, yMax: Int, x: Int, board: Board): Boolean{
        for (y in yMin+1 until yMax)
            if (board.getPieceInPosition(Vector(x, y)).isSuccess)
                return true
        return false
    }

    private fun isVertical(origin: Vector, destini: Vector): Boolean{
        if (origin.x != destini.x) return false
        if (origin.y == destini.y) return false
        return true
    }
}