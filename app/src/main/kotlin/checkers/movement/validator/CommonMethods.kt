package checkers.movement.validator

import boardGame.board.Board
import boardGame.board.Vector

fun destinationAndActualExist(board: Board, actual: Vector, destination: Vector): Boolean {
    return board.positionExists(actual) && board.positionExists(destination)
}