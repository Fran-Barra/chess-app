package chess.movementStrategy.movementStrategyFactory

import boardGame.movement.MovementStrategyFactory
import boardGame.movement.MovementValidator
import chess.movementStrategy.validators.EatSpecificPieceOnPassant

/**
 * The idOfPiece is the piece that this validator has to check if can be eaten EnPassant
 */
class EnPassantValidator(private val idOfPiece: Int): MovementStrategyFactory {
    override fun getMovementStrategy(): MovementValidator {
        return EatSpecificPieceOnPassant(idOfPiece)
    }
}