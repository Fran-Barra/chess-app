package chess.movementStrategy.updateMovementManagerOverEvent

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.Move
import boardGame.movement.MovementResult
import boardGame.movement.movementManager.Movement
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.UpdateMovementManagerOverEvent
import boardGame.piece.Piece
import chess.movementStrategy.movementPerformer.EnPassant
import chess.movementStrategy.movementStrategyFactory.EnPassantValidator
import kotlin.math.abs

object CheckIfOnEnPassant: UpdateMovementManagerOverEvent {
    override fun update(manager: MovementManager, event: MovementResult, oldGameState: Game): MovementManager {
        if (!isEventPawnStartingMovement2(event, oldGameState)) return manager
        return addEnPassantToPawnIfPossible(manager, event, oldGameState)
    }

    private fun isEventPawnStartingMovement2(event: MovementResult, game: Game): Boolean {
        if (event.movementEvents.size != 1) return false
        val move = when (val movement = event.movementEvents[0]) {
            is Move -> movement
            else -> return false
        }
        return advance2HorizontalAndItsPawn(move, game)
    }

    private fun advance2HorizontalAndItsPawn(move: Move, game: Game): Boolean {
        if (move.from.x != move.too.x) return false
        if (abs(move.from.y - move.too.y) != 2) return false

        return isPawn(move.piece)
    }

    private fun isPawn(piece: Piece) = piece.getPieceType() == 5


    private fun addEnPassantToPawnIfPossible(manager: MovementManager, event: MovementResult, oldGameState: Game): MovementManager {
        val move = when (val movement = event.movementEvents[0]) {
            is Move -> movement
            else -> return manager
        }

        val newManager = ifPieceInPossEnablePassant(move.too+Vector(1, 0), move.piece, oldGameState, manager)
        return ifPieceInPossEnablePassant(move.too + Vector(-1, 0), move.piece, oldGameState, newManager)
    }

    private fun ifPieceInPossEnablePassant(piecePos: Vector, toEat: Piece, game: Game, manager: MovementManager): MovementManager {
        val piece = when (val outcome = game.getBoard().getPieceInPosition(piecePos)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return manager
        }

        return manager.addMovement(
            piece.getPieceId(), Movement(EnPassantValidator(toEat.getPieceId()).getMovementStrategy(), EnPassant)
        )
    }

}