package checkers

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.piece.Piece
import boardGame.player.Player

//TODO: if wants to expand the game, should consider using movements
//TODO: this shit is not working
fun isPlayerAbleToEat(player: Player, board: Board): Boolean {
    for ((piece: Piece, position: Vector) in board.getPiecesAndPosition()) {
        if (!player.playerControlColor(piece.getPieceColor())) continue
        if (isPieceAbleToEat(position, board, player)) return true
    }
    return false
}

fun isPieceAbleToEat(piecePos: Vector, board: Board, player: Player): Boolean {
    val piece = when (val outcome = board.getPieceInPosition(piecePos)){
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> return false
    }
    return if (piece.getPieceType() == 0) {
        isQueenAbleToEat(piecePos, board, player)
    }
    else {
        isNormalPieceAbleToEat(piecePos, board, player)
    }
}

fun isNormalPieceAbleToEat(piecePos: Vector, board: Board, player: Player): Boolean {
    return isPieceAbleToEatInDirection(piecePos, board, player, frontIsUpDirection(player))
}

fun isQueenAbleToEat(piecePos: Vector, board: Board, player: Player): Boolean {
    return isPieceAbleToEatInDirection(piecePos, board, player, true) ||
            isPieceAbleToEatInDirection(piecePos, board, player, false)
}

private fun isPieceAbleToEatInDirection(piecePos: Vector, board: Board, player: Player, upDirection: Boolean): Boolean {
    val directionR = if (upDirection) Vector(1, -1) else Vector(1, 1)
    if (isPieceAbleToEatInDirection(directionR, board, piecePos, player)) return true

    val directionL = if (upDirection) Vector(-1, -1) else Vector(-1, 1)
    return isPieceAbleToEatInDirection(directionL, board, piecePos, player)
}

private fun isPieceAbleToEatInDirection(direction: Vector, board: Board, piecePos: Vector, player: Player): Boolean {
    val getPieceResult = board.getPieceInPosition(piecePos + direction)

    if (getPieceResult is SuccessfulOutcome) {
        if (!player.playerControlColor(getPieceResult.data.getPieceColor())) {
            val outcome = board.getPieceInPosition(piecePos + direction * 2)
            if (outcome is FailedOutcome)
                if (outcome.error.contains("No piece found in position"))
                    return true
        }
    }
    return false
}

private fun frontIsUpDirection(player: Player): Boolean {
    return player.getPlayerId() == 0
}