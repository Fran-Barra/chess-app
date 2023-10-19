package movement.movementStrategy

import board.Board
import movement.MovementStrategy
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import vector.Vector

class StraightLinesMovement: MovementStrategy {
    /**Checks if the movement is in a straight line and if it is it check for pieces in between
     and then if in the final position there is a piece if it can eat it.
    */
    override fun checkMovement(pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector,
                               destination: Vector, board: Board): Boolean {
        if (actual.x != destination.x && actual.y != destination.y) return false
        if (!board.positionExists(destination) || !board.positionExists(actual)) return false

        if (actual.x == destination.x){
            for (y in actual.y+1..<destination.y)
                if (board.getPieceInPosition(Vector(actual.x, y)).isSuccess)
                    return false
        }else{
            for (x in actual.x+1..<destination.x)
                if (board.getPieceInPosition(Vector(x, actual.x)).isSuccess)
                    return false
        }

        //TODO: move eating logic to gameManager
        val pieceInDestination: Result<Piece> = board.getPieceInPosition(destination)
        if (!pieceInDestination.isSuccess) return true
        return pieceEatingRuler.canPieceEatPiece(board.getPieceInPosition(actual).getOrThrow(),
            pieceInDestination.getOrThrow())
    }
}