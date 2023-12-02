package chess.winningConditionStrategy

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.piece.Piece
import boardGame.player.Player
import boardGame.winningConditionStrategy.WinningConditionStrategy

class TotalAnnihilationWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(game: Game): Outcome<Boolean> {
        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }
        return SuccessfulOutcome(getEnemyPieces(game.getBoard().getPiecesAndPosition(), actualPlayer).isEmpty())
    }

    private fun getEnemyPieces(pieces: List<Pair<Piece, Vector>>, actualPlayer: Player): List<Piece> {
        return pieces.filter { (p, _) -> !actualPlayer.playerControlColor(p.getPieceColor()) }.map {
            (p, _) -> p
        }
    }
}