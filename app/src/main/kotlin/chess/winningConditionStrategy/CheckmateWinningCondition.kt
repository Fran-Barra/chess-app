package chess.winningConditionStrategy

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementValidator
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
                                        pieceEatingRuler: PieceEatingRuler, pieceMovementValidator: Map<Int, MovementValidator>,
                                        specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {

        val pieceToCheck: Piece = when (val outcome = findPieceToCheck(board, actualPlayer, 0)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }
        return isCheckMate(
            pieceToCheck, actualPlayer, board, pieceEatingRuler, pieceMovementValidator, specialMovementsController
        )
    }

    private fun isCheckMate(piece: Piece, player: Player,
                            board: Board,
                            pieceEatingRuler: PieceEatingRuler,
                            pieceMovementValidator: Map<Int, MovementValidator>,
                            specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {

        val isInCheck: Boolean = when (val outcome = isPieceInCheck(piece, board, player,
            pieceEatingRuler, pieceMovementValidator, specialMovementsController)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return outcome
        }
        if (!isInCheck) return SuccessfulOutcome(false)

        val canPieceMoveToNoCheck: Boolean = when (val outcome = canCheckPieceMoveToNotCheck(piece, player, board,
            pieceEatingRuler, pieceMovementValidator, specialMovementsController)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return outcome
        }
        if (canPieceMoveToNoCheck) return SuccessfulOutcome(false)

        return canAnotherPieceSaveTheCheckPieceFromCheck(board, piece, player, pieceEatingRuler, pieceMovementValidator,
        specialMovementsController)
    }



    //TODO("Consider special movements")
    private fun canCheckPieceMoveToNotCheck(piece: Piece, player: Player,
                                            board: Board,
                                            pieceEatingRuler: PieceEatingRuler,
                                            pieceMovementValidator: Map<Int, MovementValidator>,
                                            specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {

        val strategy: MovementValidator = pieceMovementValidator[piece.getPieceType()] ?: return SuccessfulOutcome(false)

        val pieceToCheckPos: Vector = when (val outcome = findPiecePosition(piece, board)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        for ((position, _) in board.getBoardAssList()) {
            if (!strategy.checkMovement(pieceEatingRuler, player, pieceToCheckPos, position, board)) continue

            val isStillInCheck: Boolean = when (val outcome = performMovementAndEvaluateIfInCheck(board, piece,
                position, player, pieceEatingRuler, pieceMovementValidator, specialMovementsController)){
                    is SuccessfulOutcome -> outcome.data
                    is FailedOutcome -> return outcome
            }

            if (isStillInCheck) continue
            return SuccessfulOutcome(true)
        }

        return SuccessfulOutcome(false)
    }

    private fun performMovementAndEvaluateIfInCheck(board: Board, piece: Piece, destiny: Vector, actualPlayer: Player,
                                                    pieceEatingRuler: PieceEatingRuler,
                                                    pieceMovementValidator: Map<Int, MovementValidator>,
                                                    specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {
        val toEvaluateBoard: Board = board.movePiece(piece, destiny)
        return isPieceInCheck(piece, toEvaluateBoard, actualPlayer, pieceEatingRuler, pieceMovementValidator, specialMovementsController)
    }

    private fun canAnotherPieceSaveTheCheckPieceFromCheck(board: Board, checkedPiece: Piece, player: Player,
                                                          pieceEatingRuler: PieceEatingRuler,
                                                          pieceMovementValidator: Map<Int, MovementValidator>,
                                                          specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {
        for ((piece: Piece, _) in board.getPiecesAndPosition()){
            if (piece.getPieceColor() != checkedPiece.getPieceColor() || piece == checkedPiece) continue
            val canThisPieceSave: Boolean = when (val outcome = canThisPieceSaveTheCheckPiece(board, piece,
                checkedPiece, player, pieceEatingRuler, pieceMovementValidator, specialMovementsController
            )) {
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> return outcome
            }

            if (!canThisPieceSave) continue
            return SuccessfulOutcome(true)
        }
        return SuccessfulOutcome(false)
    }

    //TODO("Consider special movements")
    private fun canThisPieceSaveTheCheckPiece(board: Board, piece: Piece, checkedPiece: Piece, player: Player,
                                              pieceEatingRuler: PieceEatingRuler,
                                              pieceMovementValidator: Map<Int, MovementValidator>,
                                              specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {
        val strategy: MovementValidator = pieceMovementValidator[piece.getPieceType()] ?: return SuccessfulOutcome(false)

        val pieceToCheckPos: Vector = when (val outcome = findPiecePosition(piece, board)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        for ((position: Vector, _) in board.getBoardAssList()){
            if (position == pieceToCheckPos) continue
            if (!strategy.checkMovement(pieceEatingRuler, player, pieceToCheckPos, position, board)) continue

            val isStillInCheck: Boolean = when (val outcome = performMovementOfOtherPieceAndEvaluateIfInCheck(
                board, piece, position, checkedPiece, player,
                pieceEatingRuler, pieceMovementValidator, specialMovementsController
            )){
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> return outcome
            }

            if (isStillInCheck) continue
            return SuccessfulOutcome(true)
        }

        return SuccessfulOutcome(false)
    }

    private fun performMovementOfOtherPieceAndEvaluateIfInCheck(board: Board, piece: Piece, destiny: Vector, checkedPiece: Piece,
                                                                actualPlayer: Player,
                                                                pieceEatingRuler: PieceEatingRuler,
                                                                pieceMovementValidator: Map<Int, MovementValidator>,
                                                                specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {
        val toEvaluateBoard: Board = board.movePiece(piece, destiny)
        return isPieceInCheck(checkedPiece, toEvaluateBoard, actualPlayer, pieceEatingRuler, pieceMovementValidator, specialMovementsController)
    }

    //TODO: improve this
    private fun findPieceToCheck(board: Board, actualPlayer: Player, pieceType: Int): Outcome<Piece> {
        for ((piece: Piece, _) in board.getPiecesAndPosition()){
            if (piece.getPieceType() != pieceType) continue
            if (!actualPlayer.playerControlColor(piece.getPieceColor())) continue
            return SuccessfulOutcome(piece)
        }
        return FailedOutcome("No matching piece found")
    }

    private fun findPiecePosition(piece: Piece, board: Board): Outcome<Vector> {
        val piecesPositions: List<Pair<Piece, Vector>> = board.getPiecesAndPosition()
        for (piecePosition in piecesPositions) {
            if (piece == piecePosition.first)
                return SuccessfulOutcome(piecePosition.second)
        }
        return FailedOutcome("Piece position not found on board")
    }
}

