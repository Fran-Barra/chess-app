package chess.winningConditionStrategy

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

//TODO: didn't work, might be cause of the ChessGame
class TotalAnnihilationWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController,
                                        pieceEatingRuler: PieceEatingRuler,
                                        pieceMovementValidator: Map<Int, MovementValidator>,
                                        specialMovementsController: SpecialMovementController
    ): Outcome<Boolean> {
        return SuccessfulOutcome(getEnemyPieces(board.getPiecesAndPosition(), actualPlayer).isEmpty())
    }

    private fun getEnemyPieces(pieces: List<Pair<Piece, Vector>>, actualPlayer: Player): List<Piece> {
        return pieces.filter { (p, _) -> !actualPlayer.playerControlColor(p.getPieceColor()) }.map {
            (p, _) -> p
        }
    }
}