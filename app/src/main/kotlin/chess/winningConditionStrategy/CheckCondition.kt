package chess.winningConditionStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import boardGame.movement.SpecialMovementController
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player


fun isPieceInCheck(
    piece: Piece,
    board: Board,
    actualPlayer: Player,
    pieceEatingRuler: PieceEatingRuler,
    pieceMovementStrategy: Map<Int, MovementStrategy>,
    specialMovementsController: SpecialMovementController
): Result<Boolean> {


    val pieceToCheckPos: Result<Vector> = getPieceToCheckPosition(piece, board)
    if (pieceToCheckPos.isFailure) return Result.failure(pieceToCheckPos.exceptionOrNull()!!)

    return Result.success(isPositionOnCheck(piece, pieceToCheckPos.getOrNull()!!, board, actualPlayer, pieceEatingRuler,
        pieceMovementStrategy, specialMovementsController))
}

private fun getPieceToCheckPosition(piece: Piece, board: Board): Result<Vector> {
    val piecesPositions: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
    for (piecePosition in piecesPositions) {
        if (piece == piecePosition.first)
            return Result.success(piecePosition.second)
    }
    return Result.failure(Exception("Piece position not found on board"))
}

private fun isPositionOnCheck(
    toCheckPiece: Piece,
    toCheckPiecePosition: Vector,
    board: Board,
    actualPlayer: Player,
    pieceEatingRuler: PieceEatingRuler,
    pieceMovementStrategy: Map<Int, MovementStrategy>,
    specialMovementsController: SpecialMovementController
): Boolean {
    val piecesAbleToCheck: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
        .filter {(piece: Piece, _) -> pieceEatingRuler.canPieceEatPiece(piece, toCheckPiece)}


    for ((piece: Piece, pos: Vector) in piecesAbleToCheck){
        if (!canPieceMoveToToCheckPiecePosition(piece, pos, toCheckPiecePosition, board, actualPlayer, pieceEatingRuler,
                pieceMovementStrategy, specialMovementsController)
            ) continue
        return true
    }
    return false
}

//TODO: consider special cases: the one that say that the movement is available but dont move to that place.
private fun canPieceMoveToToCheckPiecePosition(piece: Piece, piecePosition: Vector, destiny: Vector,
                                               board: Board,
                                               actualPlayer: Player,
                                               pieceEatingRuler: PieceEatingRuler,
                                               pieceMovementStrategy: Map<Int, MovementStrategy>,
                                               specialMovementsController: SpecialMovementController
): Boolean {
    val movementStrategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()]?: return false

    return movementStrategy.checkMovement(pieceEatingRuler, actualPlayer, piecePosition, destiny, board);
}

