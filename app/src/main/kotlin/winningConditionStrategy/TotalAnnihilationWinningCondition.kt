package winningConditionStrategy

import board.Board
import movement.MovementStrategy
import movement.SpecialMovementController
import piece.Piece
import pieceEatingRuler.PieceEatingRuler
import player.Player
import turnsController.TurnsController
import vector.Vector

class TotalAnnihilationWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController,
                                        pieceEatingRuler: PieceEatingRuler,
                                        pieceMovementStrategy: Map<Int, MovementStrategy>,
                                        specialMovementsController: SpecialMovementController
    ): Result<Boolean> {
        return Result.success(getEnemyPieces(board.getPiecesAndPosition(), actualPlayer).isEmpty())
    }

    private fun getEnemyPieces(pieces: List<Pair<Piece, Vector>>, actualPlayer: Player): List<Piece> {
        return pieces.filter { (p, _) -> !actualPlayer.playerControlColor(p.getPieceColor()) }.map {
            (p, v) -> p
        }
    }
}