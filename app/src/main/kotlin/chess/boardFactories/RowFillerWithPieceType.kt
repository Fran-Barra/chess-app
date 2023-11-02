package chess.boardFactories

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.board.boardFactory.BoardFiller
import boardGame.piece.BasicPiece

class RowFillerWithPieceType(private val type: Int, private val color: Int, private val row: Int): BoardFiller {
    override fun fillBoard(board: Board): Board {
        var filledBoard = board
        for (x in 1..8)
            filledBoard = filledBoard.addPiece(BasicPiece(type, color), Vector(x, row))
        return filledBoard
    }
}