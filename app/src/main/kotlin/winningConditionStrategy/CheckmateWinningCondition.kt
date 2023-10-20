package winningConditionStrategy

import board.Board
import movement.MovementStrategy
import movement.SpecialMovementController
import pieceEatingRuler.PieceEatingRuler
import player.Player
import turnsController.TurnsController

class CheckmateWinningCondition: WinningConditionStrategy {
    override fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController,
        pieceEatingRuler: PieceEatingRuler, pieceMovementStrategy: Map<Int, MovementStrategy>,
        specialMovementsController: SpecialMovementController): Result<Boolean> {
        TODO("Not yet implemented")
    }
}