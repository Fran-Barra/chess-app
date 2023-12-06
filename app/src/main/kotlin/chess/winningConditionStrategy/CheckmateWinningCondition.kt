package chess.winningConditionStrategy

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.BaseGame
import boardGame.game.Game
import boardGame.movement.MovementPerformer
import boardGame.movement.MovementResult
import boardGame.piece.Piece
import boardGame.player.Player
import boardGame.winningConditionStrategy.WinningConditionStrategy


//TODO: clean up code
//TODO: Also consider that a player can control multiple colors
class CheckmateWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(game: Game): Outcome<Boolean> {
        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }
        val pieceToCheck: Piece = when (val outcome = findPieceToCheck(game.getBoard(), actualPlayer, 0)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }
        return isCheckMate(pieceToCheck, game)
    }

    private fun isCheckMate(pieceToCheck: Piece, game: Game): Outcome<Boolean> {

        val isInCheck: Boolean = when (val outcome = isPieceInCheck(pieceToCheck, game)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return outcome
        }
        if (!isInCheck) return SuccessfulOutcome(false)

        val canPieceMoveToNoCheck: Boolean = when (val outcome = canCheckPieceMoveToNotCheck(pieceToCheck, game)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return outcome
        }
        if (canPieceMoveToNoCheck) return SuccessfulOutcome(false)

        return when (val outcome = canAnotherPieceSaveTheCheckPieceFromCheck(pieceToCheck, game)) {
            is SuccessfulOutcome -> SuccessfulOutcome(!outcome.data)
            is FailedOutcome -> outcome
        }
    }



    private fun canCheckPieceMoveToNotCheck(piece: Piece, game: Game): Outcome<Boolean> {
        val pieceToCheckPos: Vector = when (val outcome = findPiecePosition(piece, game.getBoard())) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        for ((position, _) in game.getBoard().getBoardAssList()) {
            val movementPerformer: MovementPerformer = when (val outcome = game.getMovementManager()
                .findValidMovementPerformer(actualPlayer, pieceToCheckPos, position, game)) {
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> continue
            }

            val isStillInCheck: Boolean = when (val outcome =
                performMovementAndEvaluateIfInCheck(piece, movementPerformer, pieceToCheckPos, position, game)){
                    is SuccessfulOutcome -> outcome.data
                    is FailedOutcome -> return outcome
            }

            if (isStillInCheck) continue
            return SuccessfulOutcome(true)
        }

        return SuccessfulOutcome(false)
    }

    private fun performMovementAndEvaluateIfInCheck(
        piece: Piece, movementPerformer: MovementPerformer, from: Vector, too: Vector, game: Game
    ): Outcome<Boolean> {
        val newGameState = when (val outcome = performMovement(from, too, movementPerformer, game)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }
        return isPieceInCheck(piece, newGameState)
    }

    private fun canAnotherPieceSaveTheCheckPieceFromCheck(checkedPiece: Piece, game: Game): Outcome<Boolean> {
        val piecesToMove = game.getBoard().getPiecesAndPosition().filter {
            it.first.getPieceColor() == checkedPiece.getPieceColor() && it.first != checkedPiece
        }
        for ((piece: Piece, _) in piecesToMove){
            //if (piece.getPieceColor() != checkedPiece.getPieceColor() || piece == checkedPiece) continue
            val canThisPieceSave: Boolean = when (val outcome = canThisPieceSaveTheCheckPiece(piece, checkedPiece, game)) {
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> return outcome
            }

            if (!canThisPieceSave) continue
            return SuccessfulOutcome(true)
        }
        return SuccessfulOutcome(false)
    }
    private fun canThisPieceSaveTheCheckPiece(piece: Piece, checkedPiece: Piece, game: Game): Outcome<Boolean> {
        val pieceToCheckPos: Vector = when (val outcome = findPiecePosition(piece, game.getBoard())) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        for ((position: Vector, _) in game.getBoard().getBoardAssList()){
            if (position == pieceToCheckPos) continue
            val movementPerformer: MovementPerformer = when (val outcome = game.getMovementManager()
                .findValidMovementPerformer(actualPlayer, pieceToCheckPos, position, game)) {
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> continue
            }

            val isStillInCheck: Boolean = when (val outcome = performMovementOfOtherPieceAndEvaluateIfInCheck(
                piece, position, movementPerformer, checkedPiece, game
            )){
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> return outcome
            }

            if (isStillInCheck) continue
            return SuccessfulOutcome(true)
        }

        return SuccessfulOutcome(false)
    }

    private fun performMovementOfOtherPieceAndEvaluateIfInCheck(
        piece: Piece, destiny: Vector, movementPerformer: MovementPerformer, checkedPiece: Piece, game: Game
    ): Outcome<Boolean> {
        val piecePos: Vector = when (val outcome = findPiecePosition(piece, game.getBoard())) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val newGameState = when (val outcome = performMovement(piecePos, destiny, movementPerformer, game)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        return isPieceInCheck(checkedPiece, newGameState)
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

    private fun performMovement(from: Vector, too: Vector, movementPerformer: MovementPerformer, game: Game): Outcome<Game> {
        val movementResult: MovementResult = when (val outcome = movementPerformer.performMovement(from, too, game.getBoard())) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }



        //TODO: modify movementManager given the events

        //TODO use new movementManager and new movementManagerController
        return SuccessfulOutcome(BaseGame(movementResult.newBoard,
            game.getTurnController().updateTurnController(movementResult),
            game.getPieceEatingRuler(), game.getMovementManager(),
            game.getMovementManagerController(), game.getWinningConditions()
        ))
    }
}

