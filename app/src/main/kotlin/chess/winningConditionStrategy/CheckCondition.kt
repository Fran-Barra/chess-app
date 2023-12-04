package chess.winningConditionStrategy

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.piece.Piece


fun isPieceInCheck(piece: Piece, game: Game): Outcome<Boolean> {


    val pieceToCheckPos: Vector = when (val outcome = getPieceToCheckPosition(piece, game.getBoard())) {
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> return FailedOutcome(outcome.error)
    }

    return SuccessfulOutcome(isPositionOnCheck(piece, pieceToCheckPos, game))
}

private fun getPieceToCheckPosition(piece: Piece, board: Board): Outcome<Vector> {
    val piecesPositions: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
    for (piecePosition in piecesPositions) {
        if (piece == piecePosition.first)
            return SuccessfulOutcome(piecePosition.second)
    }
    return FailedOutcome("Piece position not found on board")
}

private fun isPositionOnCheck(toCheckPiece: Piece, toCheckPiecePosition: Vector, game: Game): Boolean {
    val piecesAbleToCheck: List<Pair<Piece, Vector>> = game.getBoard().getPiecesAndPosition()
        .filter {(piece: Piece, _) -> game.getPieceEatingRuler().canPieceEatPiece(piece, toCheckPiece)}


    for ((_: Piece, pos: Vector) in piecesAbleToCheck){
        if (!canPieceMoveToToCheckPiecePosition(pos, toCheckPiecePosition, game)
            ) continue
        return true
    }
    return false
}

//TODO: consider special cases: the one that say that the movement is available but dont move to that place.
private fun canPieceMoveToToCheckPiecePosition(piecePosition: Vector, destiny: Vector,
                                               game: Game): Boolean {
    //TODO: consider doing something different in case is false
    val actualPlayer = when (val outcome = game.getActualPlayer()) {
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> return false
    }
    val movementPerformer = when (val outcome = game.getMovementManager().findValidMovementPerformer(
        actualPlayer, piecePosition, destiny, game)
    ){
        is SuccessfulOutcome -> outcome.data
        is FailedOutcome -> return false
    }

    //TODO: validate that performing the movement may destroy the king
    //TODO: consider the movements that dont take the king as target but is a target by movement performer
    return true
}

