package boardGame.movement

import boardGame.board.Board

interface SpecialMovement {
    fun executeSpecialMovement(board: Board): Result<Board>
}