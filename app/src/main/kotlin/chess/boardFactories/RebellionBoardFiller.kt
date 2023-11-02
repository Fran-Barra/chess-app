package chess.boardFactories

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.board.boardFactory.BoardFiller
import boardGame.piece.BasicPiece

class RebellionBoardFiller: BoardFiller {
    override fun fillBoard(board: Board): Board {
        var filledBoard = RowFillerWithPieceType(5, 1, 1).fillBoard(board)
        filledBoard = RowFillerWithPieceType(5, 1, 2).fillBoard(filledBoard)
        filledBoard = RowFillerWithPieceType(5, 1, 3).fillBoard(filledBoard)

        return fillWhitePieces(filledBoard, 0, 9, 3)
    }

    private fun fillWhitePieces(board: Board, color: Int, yLowerRow: Int, xLeftestValue: Int): Board {
        //king and queen
        var fillerBoard = board.addPiece(BasicPiece(0, color), Vector(xLeftestValue+2, yLowerRow))
        fillerBoard = fillerBoard.addPiece(BasicPiece(1, color), Vector(xLeftestValue+1, yLowerRow))

        //princesses
        fillerBoard = fillerBoard.addPiece(BasicPiece(6, color), Vector(xLeftestValue+2, yLowerRow-1))
        fillerBoard = fillerBoard.addPiece(BasicPiece(6, color), Vector(xLeftestValue+1, yLowerRow-1))

        //knights
        fillerBoard = fillerBoard.addPiece(BasicPiece(3, color), Vector(xLeftestValue, yLowerRow))
        fillerBoard = fillerBoard.addPiece(BasicPiece(3, color), Vector(xLeftestValue+3, yLowerRow))
        fillerBoard = fillerBoard.addPiece(BasicPiece(3, color), Vector(xLeftestValue, yLowerRow-1))
        fillerBoard = fillerBoard.addPiece(BasicPiece(3, color), Vector(xLeftestValue+3, yLowerRow-1))

        //rooks
        fillerBoard = fillerBoard.addPiece(BasicPiece(4, color), Vector(xLeftestValue,yLowerRow-2))
        fillerBoard = fillerBoard.addPiece(BasicPiece(4, color), Vector(xLeftestValue+1,yLowerRow-2))
        fillerBoard = fillerBoard.addPiece(BasicPiece(4, color), Vector(xLeftestValue+2,yLowerRow-2))
        fillerBoard = fillerBoard.addPiece(BasicPiece(4, color), Vector(xLeftestValue+3,yLowerRow-2))

        return fillerBoard
    }
}