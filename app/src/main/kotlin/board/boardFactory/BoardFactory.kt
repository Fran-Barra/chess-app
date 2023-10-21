package board.boardFactory

import board.Board

interface BoardFactory {
    fun createNewEmptyBoard(): Board
}