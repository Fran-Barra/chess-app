package chess.winningConditionStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
import boardGame.movement.SpecialMovementController
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.winningConditionStrategy.WinningConditionStrategy
import boardGame.turnsController.TurnsController


//TODO: clean up code
//TODO: Also consider that a player can control multiple colors
class CheckmateWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController,
                                        pieceEatingRuler: PieceEatingRuler, pieceMovementStrategy: Map<Int, MovementStrategy>,
                                        specialMovementsController: SpecialMovementController
    ): Result<Boolean> {

        val pieceToCheck: Piece = findPieceToCheck()
        return isCheckMate(
            pieceToCheck, actualPlayer, board, pieceEatingRuler, pieceMovementStrategy, specialMovementsController
        )
    }

    private fun isCheckMate(piece: Piece, player: Player,
                            board: Board,
                            pieceEatingRuler: PieceEatingRuler,
                            pieceMovementStrategy: Map<Int, MovementStrategy>,
                            specialMovementsController: SpecialMovementController
    ): Result<Boolean> {

        val isInCheckResult: Result<Boolean> = isPieceInCheck(piece, board, player,
            pieceEatingRuler, pieceMovementStrategy, specialMovementsController)
        if (isInCheckResult.isFailure) return isInCheckResult
        if (!isInCheckResult.getOrNull()!!) return isInCheckResult

        val canPieceMoveToNoCheckResult: Result<Boolean> = canCheckPieceMoveToNotCheck(piece, player, board,
            pieceEatingRuler, pieceMovementStrategy, specialMovementsController)
        if (canPieceMoveToNoCheckResult.isFailure) return canPieceMoveToNoCheckResult
        if (canPieceMoveToNoCheckResult.getOrNull()!!) return Result.success(false)

        return canAnotherPieceSaveTheCheckPieceFromCheck(board, piece, player, pieceEatingRuler, pieceMovementStrategy,
        specialMovementsController)
    }



    //TODO("Consider special movements")
    private fun canCheckPieceMoveToNotCheck(piece: Piece, player: Player,
                                            board: Board,
                                            pieceEatingRuler: PieceEatingRuler,
                                            pieceMovementStrategy: Map<Int, MovementStrategy>,
                                            specialMovementsController: SpecialMovementController
    ): Result<Boolean> {

        val strategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()] ?: return Result.success(false)

        val pieceToCheckPosR: Result<Vector> = findPiecePosition(piece, board)
        if (pieceToCheckPosR.isFailure) return Result.failure(pieceToCheckPosR.exceptionOrNull()!!)
        val pieceToCheckPos: Vector = pieceToCheckPosR.getOrNull()!!

        for ((position, _) in board.getBoardAssList()) {
            if (strategy.checkMovement(pieceEatingRuler, player, pieceToCheckPos, position, board)) {
                val isStillInCheckResult: Result<Boolean> = performMovementAndEvaluateIfInCheck(board, piece, position,
                    player, pieceEatingRuler, pieceMovementStrategy, specialMovementsController)

                if (isStillInCheckResult.isFailure) return isStillInCheckResult
                if (isStillInCheckResult.getOrNull()!!) continue
                return Result.success(true)
            }
        }
        return Result.success(false)
    }

    private fun performMovementAndEvaluateIfInCheck(board: Board, piece: Piece, destiny: Vector, actualPlayer: Player,
                                           pieceEatingRuler: PieceEatingRuler,
                                           pieceMovementStrategy: Map<Int, MovementStrategy>,
                                           specialMovementsController: SpecialMovementController
    ): Result<Boolean> {
        val toEvaluateBoard: Board = board.movePiece(piece, destiny)
        return isPieceInCheck(piece, toEvaluateBoard, actualPlayer, pieceEatingRuler, pieceMovementStrategy, specialMovementsController)
    }

    private fun canAnotherPieceSaveTheCheckPieceFromCheck(board: Board, checkedPiece: Piece, player: Player,
                                                          pieceEatingRuler: PieceEatingRuler,
                                                          pieceMovementStrategy: Map<Int, MovementStrategy>,
                                                          specialMovementsController: SpecialMovementController
    ): Result<Boolean> {
        for ((piece: Piece, _) in board.getPiecesAndPosition()){
            if (piece.getPieceColor() != checkedPiece.getPieceColor() || piece == checkedPiece) continue
            val canThisPieceSaveResult: Result<Boolean> = canThisPieceSaveTheCheckPiece(board, piece, checkedPiece,
                player, pieceEatingRuler, pieceMovementStrategy, specialMovementsController
            )

            if (canThisPieceSaveResult.isFailure) return canThisPieceSaveResult
            if (!canThisPieceSaveResult.getOrNull()!!) continue
            return Result.success(true)
        }
        return Result.success(false)
    }

    //TODO("Consider special movements")
    private fun canThisPieceSaveTheCheckPiece(board: Board, piece: Piece, checkedPiece: Piece, player: Player,
                                              pieceEatingRuler: PieceEatingRuler,
                                              pieceMovementStrategy: Map<Int, MovementStrategy>,
                                              specialMovementsController: SpecialMovementController
    ): Result<Boolean> {
        val strategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()] ?: return Result.success(false)

        val pieceToCheckPosR: Result<Vector> = findPiecePosition(piece, board)
        if (pieceToCheckPosR.isFailure) return Result.failure(pieceToCheckPosR.exceptionOrNull()!!)
        val pieceToCheckPos: Vector = pieceToCheckPosR.getOrNull()!!

        for ((position: Vector, _) in board.getBoardAssList()){
            if (position == pieceToCheckPos) continue
            if (strategy.checkMovement(pieceEatingRuler, player, pieceToCheckPos, position, board)) {
                val isStillInCheckResult: Result<Boolean> = performMovementOfOtherPieceAndEvaluateIfInCheck(
                    board, piece, position, checkedPiece,
                    player, pieceEatingRuler, pieceMovementStrategy, specialMovementsController
                )

                if (isStillInCheckResult.isFailure) return isStillInCheckResult
                if (isStillInCheckResult.getOrNull()!!) continue
                return Result.success(true)
            }
        }
        return Result.success(false)
    }

    private fun performMovementOfOtherPieceAndEvaluateIfInCheck(board: Board, piece: Piece, destiny: Vector, checkedPiece: Piece,
                                                       actualPlayer: Player,
                                                        pieceEatingRuler: PieceEatingRuler,
                                                        pieceMovementStrategy: Map<Int, MovementStrategy>,
                                                        specialMovementsController: SpecialMovementController
    ): Result<Boolean> {
        val toEvaluateBoard: Board = board.movePiece(piece, destiny)
        return isPieceInCheck(checkedPiece, toEvaluateBoard, actualPlayer, pieceEatingRuler, pieceMovementStrategy, specialMovementsController)
    }

    private fun findPieceToCheck(): Piece {
        TODO("Not implemented")
    }

    private fun findPiecePosition(piece: Piece, board: Board): Result<Vector> {
        val piecesPositions: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
        for (piecePosition in piecesPositions) {
            if (piece == piecePosition.first)
                return Result.success(piecePosition.second)
        }
        return Result.failure(Exception("Piece position not found on board"))
    }
}

