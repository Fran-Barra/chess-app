package chess.winningConditionStrategy

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementStrategy
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
                                        pieceMovementStrategy: Map<Int, MovementStrategy>,
                                        specialMovementsController: SpecialMovementController
    ): Result<Boolean> {
        return Result.success(getEnemyPieces(board.getPiecesAndPosition(), actualPlayer).isEmpty())
    }

    private fun getEnemyPieces(pieces: List<Pair<Piece, Vector>>, actualPlayer: Player): List<Piece> {
        return pieces.filter { (p, _) -> !actualPlayer.playerControlColor(p.getPieceColor()) }.map {
            (p, _) -> p
        }
    }
}