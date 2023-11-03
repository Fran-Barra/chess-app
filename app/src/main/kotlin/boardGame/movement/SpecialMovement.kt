package boardGame.movement

import Outcome
import boardGame.board.Board

interface SpecialMovement {
    fun executeSpecialMovement(board: Board): Outcome<Board>
}