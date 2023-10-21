package board.boardFactory

import board.Board
import piece.BasicPiece
import vector.Vector

class RowFillerWithPieceType(private val type: Int, private val color: Int, private val row: Int): BoardFiller {
    override fun fillBoard(board: Board): Board {
        var filledBoard = board
        for (x in 1..8)
            filledBoard = filledBoard.addPiece(BasicPiece(type, color), Vector(x, row))
        return filledBoard
    }
}