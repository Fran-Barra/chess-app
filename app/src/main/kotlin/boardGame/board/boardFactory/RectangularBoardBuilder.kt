package boardGame.board.boardFactory

import boardGame.board.Board
import boardGame.board.MapBoard
import boardGame.board.Vector
import boardGame.piece.Piece

class RectangularBoardBuilder(private val columns: Int, private val rows: Int): BoardFactory {
    override fun createNewEmptyBoard(): Board {
        val board: MutableMap<Vector, Piece?> = mutableMapOf()
        for (x in 1..columns)
            for (y in 1 .. rows)
                board.put(Vector(x, y), null)
        return MapBoard(board)
    }
}