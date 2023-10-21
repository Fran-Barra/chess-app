package board.boardFactory

import board.Board
import board.MapBoard
import piece.Piece
import vector.Vector

class RectangularBoardBuilder(private val columns: Int, private val rows: Int): BoardFactory {
    override fun createNewEmptyBoard(): Board {
        val board: MutableMap<Vector, Piece?> = mutableMapOf()
        for (x in 1..columns)
            for (y in 1 .. rows)
                board.put(Vector(x, y), null)
        return MapBoard(board)
    }
}