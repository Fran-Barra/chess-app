package boardGame.board.boardFactory

import boardGame.board.Board

interface BoardFactory {
    fun createNewEmptyBoard(): Board
}