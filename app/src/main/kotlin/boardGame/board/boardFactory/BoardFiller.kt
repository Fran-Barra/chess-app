package boardGame.board.boardFactory

import boardGame.board.Board

interface BoardFiller {
    fun fillBoard(board: Board): Board
}