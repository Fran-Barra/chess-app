package movement

import board.Board

interface SpecialMovement {
    fun executeSpecialMovement(board: Board): Result<Board>
}