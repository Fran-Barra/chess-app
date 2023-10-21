package board.boardFactory

import board.Board

interface BoardFiller {
    fun fillBoard(board: Board): Board
}