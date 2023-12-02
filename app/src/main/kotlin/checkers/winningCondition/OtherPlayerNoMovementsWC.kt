package checkers.winningCondition

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.movementManager.MovementManager
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
        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return listOf()
        }
        return game.getBoard().getPiecesAndPosition().filter { (p: Piece, _) ->
            actualPlayer.playerControlColor(p.getPieceColor())}
            .map { (_, v) -> v }

    }

    private fun isPieceAbleToDoAnyMovement(piecePos: Vector, game: Game): Boolean {
        val positions: List<Vector> = game.getBoard().getBoardAssList().map { (p, _) -> p }
        val movementM: MovementManager = game.getMovementManager()

        //TODO: consider doing something different here
        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }

        for (pos in positions) {
            if (movementM.findValidMovementPerformer(
                    game.getPieceEatingRuler(), actualPlayer, piecePos, pos, game.getBoard()
            ) is SuccessfulOutcome) return true
        }
        return false
    }
}