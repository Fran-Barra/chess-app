package checkers.boardFactory

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.board.boardFactory.BoardFiller
import boardGame.piece.BasicPiece

object CheckersBoardFiller: BoardFiller {
    override fun fillBoard(board: Board): Board {
        var filledBoard = board

        filledBoard = fillSpacedRow(filledBoard, 2, 10, 1, 1)
        filledBoard = fillSpacedRow(filledBoard, 1, 10, 2, 1)
        filledBoard = fillSpacedRow(filledBoard, 2, 10, 3, 1)

        filledBoard = fillSpacedRow(filledBoard, 1, 10, 8, 0)
        filledBoard = fillSpacedRow(filledBoard, 2, 10, 7, 0)
        filledBoard = fillSpacedRow(filledBoard, 1, 10, 6, 0)

        return filledBoard
    }

    private fun fillSpacedRow(board: Board, xMin: Int, xMax: Int, row: Int, player: Int): Board {
        var filledBoard = board
        for (x in xMin..xMax step 2){
            filledBoard = filledBoard.addPiece(BasicPiece(1, player), Vector(x, row))
        }
        return filledBoard
    }
}