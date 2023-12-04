package chess.movementStrategy.validators

import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementValidator
import boardGame.player.Player

object HorizontalMovement: MovementValidator {
    override fun validate(player: Player, actual: Vector, destination: Vector, game: Game): Boolean {
        if (!isHorizontal(actual, destination)) return false
        return !checkIfPieceInMiddlePath(actual, destination, game.getBoard())
    }

    private fun isHorizontal(origin: Vector, destini: Vector): Boolean{
        if (origin.y != destini.y) return false
        if (origin.x == destini.x) return false
        return true
    }

    private fun checkIfPieceInMiddlePath(origin: Vector, destini: Vector, board: Board): Boolean{
        if (origin.x < destini.x) return checkIfPieceBetweenXMinAndXMax(origin.x, destini.x, origin.y, board)
        return checkIfPieceBetweenXMinAndXMax(destini.x, origin.x, origin.y, board)
    }

    private fun checkIfPieceBetweenXMinAndXMax(xMin: Int, xMax: Int, y: Int, board: Board): Boolean{
        for (x in xMin+1 until xMax)
            if (board.getPieceInPosition(Vector(x, y)) is SuccessfulOutcome)
                return true
        return false
    }
}