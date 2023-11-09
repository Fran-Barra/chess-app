package checkers.winningCondition

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementManager
import boardGame.piece.Piece
import boardGame.winningConditionStrategy.WinningConditionStrategy

/**
 *Since we have the next state of the game, we only have to check if the player can make movements or not
 */
class OtherPlayerNoMovementsWC: WinningConditionStrategy {
    override fun checkWinningConditions(game: Game): Outcome<Boolean> {
        for (piecePos in getPlayerPiecesPositions(game))
            if (isPieceAbleToDoAnyMovement(piecePos, game)) return SuccessfulOutcome(false)
        return SuccessfulOutcome(true)
    }

    private fun getPlayerPiecesPositions(game: Game): List<Vector> {
        return game.getBoard().getPiecesAndPosition().filter { (p, _) ->
            game.getActualPlayer().playerControlColor(p.getPieceColor())}
            .map { (_, v) -> v }

    }

    private fun isPieceAbleToDoAnyMovement(piecePos: Vector, game: Game): Boolean {
        val positions: List<Vector> = game.getBoard().getBoardAssList().map { (p, _) -> p }
        val movementM: MovementManager = game.getMovementManager()
        for (pos in positions) {
            if (movementM.findValidMovementPerformer(
                    game.getPieceEatingRuler(), game.getActualPlayer(), piecePos, pos, game.getBoard()
            ) is SuccessfulOutcome) return true
        }
        return false
    }
}