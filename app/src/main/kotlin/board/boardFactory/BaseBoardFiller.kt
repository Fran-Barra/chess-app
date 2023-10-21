package board.boardFactory

import board.Board
import piece.BasicPiece
import vector.Vector

class BaseBoardFiller: BoardFiller {
    override fun fillBoard(board: Board): Board {
        var filledBoard = board
        filledBoard = RowFillerWithPieceType(5, 0, 7).fillBoard(filledBoard)
        filledBoard = RowFillerWithPieceType(5, 1, 2).fillBoard(filledBoard)

        filledBoard = fillRowWithBasicPieces(filledBoard, 0, 8)
        filledBoard = fillRowWithBasicPieces(filledBoard, 1, 1)

        return filledBoard
    }

    private fun fillRowWithBasicPieces(board: Board, color: Int, row: Int): Board {
        var fillBoard: Board = board
        //rooks
        fillBoard = fillBoard.addPiece(BasicPiece(4, color), Vector(8, row))
        fillBoard = fillBoard.addPiece(BasicPiece(4, color), Vector(1, row))

        //knights
        fillBoard = fillBoard.addPiece(BasicPiece(3, color), Vector(7, row))
        fillBoard = fillBoard.addPiece(BasicPiece(3, color), Vector(2, row))

        //bishops
        fillBoard = fillBoard.addPiece(BasicPiece(2, color), Vector(6, row))
        fillBoard = fillBoard.addPiece(BasicPiece(2, color), Vector(3, row))

        //king and queen
        fillBoard = fillBoard.addPiece(BasicPiece(0, color), Vector(5, row))
        fillBoard = fillBoard.addPiece(BasicPiece(1, color), Vector(4, row))

        return fillBoard
    }
}