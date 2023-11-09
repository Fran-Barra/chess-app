package chess.movementStrategy.validators

import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

object VerticalMovement: MovementValidator {
    override fun validate(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                          destination: Vector, board: Board
    ): Boolean {
        if (!isVertical(actual, destination)) return false
        return !checkIfPieceInMiddlePath(actual, destination, board)
    }

    private fun checkIfPieceInMiddlePath(origin: Vector, destini: Vector, board: Board): Boolean{
        if (origin.y < destini.y) return checkIfPieceBetweenYMinAndYMax(origin.y, destini.y, origin.x, board)
        return checkIfPieceBetweenYMinAndYMax(destini.y, origin.y, origin.x, board)
    }

    private fun checkIfPieceBetweenYMinAndYMax(yMin: Int, yMax: Int, x: Int, board: Board): Boolean{
        for (y in yMin+1 until yMax)
            if (board.getPieceInPosition(Vector(x, y)) is SuccessfulOutcome)
                return true
        return false
    }

    private fun isVertical(origin: Vector, destini: Vector): Boolean{
        if (origin.x != destini.x) return false
        if (origin.y == destini.y) return false
        return true
    }
}