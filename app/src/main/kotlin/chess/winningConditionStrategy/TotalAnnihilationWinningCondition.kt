package chess.winningConditionStrategy

import Outcome
import SuccessfulOutcome
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.piece.Piece
import boardGame.player.Player
import boardGame.winningConditionStrategy.WinningConditionStrategy

class TotalAnnihilationWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(game: Game): Outcome<Boolean> {
        return SuccessfulOutcome(getEnemyPieces(game.getBoard().getPiecesAndPosition(), game.getActualPlayer()).isEmpty())
    }

    private fun getEnemyPieces(pieces: List<Pair<Piece, Vector>>, actualPlayer: Player): List<Piece> {
        return pieces.filter { (p, _) -> !actualPlayer.playerControlColor(p.getPieceColor()) }.map {
            (p, _) -> p
        }
    }
}