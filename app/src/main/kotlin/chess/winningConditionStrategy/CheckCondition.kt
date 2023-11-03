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
    val markedBoard: Map<Vector, Boolean> = generateMarkedBoard(board, piece, actualPlayer, pieceMovementStrategy,
        specialMovementsController, pieceEatingRuler)

    val pieceToCheckPos: Result<Vector> = getPieceToCheckPosition(piece, board)
    if (pieceToCheckPos.isFailure) return Result.failure(pieceToCheckPos.exceptionOrNull()!!)

    return Result.success(isPositionOnCheck(pieceToCheckPos.getOrNull()!!, markedBoard))
}
//TODO: re do this fucking methods, Right now all position are calculated to see if the piece in check.
//But the only important thing is if one piece of other player has the ability to move to the piece position.
//Also consider that a player can control multiple colors

private fun isPositionOnCheck(position: Vector, markedBoard: Map<Vector, Boolean>): Boolean =
    markedBoard[position] ?: false

private fun getPieceToCheckPosition(piece: Piece, board: Board): Result<Vector> {
    val piecesPositions: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
    for (piecePosition in piecesPositions) {
        if (piece == piecePosition.first)
            return Result.success(piecePosition.second)
    }
    return Result.failure(Exception("Piece position not found on board"))
}

private fun generateMarkedBoard(
    board: Board,
    pieceToCheck: Piece,
    actualPlayer: Player,
    pieceMovementStrategy: Map<Int, MovementStrategy>,
    specialMovementsController: SpecialMovementController,
    pieceEatingRuler: PieceEatingRuler
    ): Map<Vector, Boolean> {
    val markedBoard: MutableMap<Vector, Boolean> = generateFalseMarkedBoard(board)
    return fillMarkedBoard(markedBoard, pieceToCheck, board, pieceMovementStrategy, specialMovementsController,
        pieceEatingRuler, actualPlayer)
}


private fun generateFalseMarkedBoard(board: Board): MutableMap<Vector, Boolean> {
    val boardList: List<Pair<Vector, Piece?>> = board.getBoardAssList()
    val mutableMap: MutableMap<Vector, Boolean> = mutableMapOf()
    boardList.forEach{(v, p) -> mutableMap[v] = (p != null)}
    return mutableMap
}

private fun fillMarkedBoard(
    falseMarkedBoard: MutableMap<Vector, Boolean>,
    pieceToCheck: Piece,
    board: Board,
    pieceMovementStrategy: Map<Int, MovementStrategy>,
    specialMovementsController: SpecialMovementController,
    pieceEatingRuler: PieceEatingRuler,
    actualPlayer: Player
): MutableMap<Vector, Boolean> {
    var markedBoarAux: MutableMap<Vector, Boolean> = falseMarkedBoard
    for (piece in board.getPiecesAndPosition()) {
        if (pieceToCheck.getPieceColor() != piece.first.getPieceColor()) continue
        markedBoarAux = pieceBlockPosition(piece, markedBoarAux, board, pieceMovementStrategy,
            specialMovementsController, pieceEatingRuler, actualPlayer)
    }
    return markedBoarAux
}

//TODO("consider special movements")
private fun pieceBlockPosition(piecePair: Pair<Piece, Vector>,
                               markedBoard: MutableMap<Vector, Boolean>,
                               board: Board,
                               pieceMovementStrategy: Map<Int, MovementStrategy>,
                               specialMovementsController: SpecialMovementController,
                               pieceEatingRuler: PieceEatingRuler,
                               actualPlayer: Player
):
        MutableMap<Vector, Boolean> {

    val markedBoarAux: MutableMap<Vector, Boolean> = markedBoard;
    val piece: Piece = piecePair.first
    if (!actualPlayer.playerControlColor(piece.getPieceColor())) return markedBoarAux

    val movementStrategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()] ?: return markedBoarAux

    for (position in markedBoarAux) {
        val previousValue: Boolean? = markedBoarAux[position.key]
        if (previousValue == null) {
            markedBoarAux[position.key] = movementStrategy
                .checkMovement(pieceEatingRuler, actualPlayer, piecePair.second, position.key, board)
        }else{
            if (previousValue) continue
            markedBoarAux[position.key] = movementStrategy
                .checkMovement(pieceEatingRuler, actualPlayer, piecePair.second, position.key, board)
        }
    }
    return markedBoarAux
}


