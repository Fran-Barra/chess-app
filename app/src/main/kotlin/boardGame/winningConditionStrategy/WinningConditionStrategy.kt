package boardGame.winningConditionStrategy

import Outcome
import boardGame.board.Board
import boardGame.movement.MovementStrategy
import boardGame.movement.SpecialMovementController
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.turnsController.TurnsController

interface WinningConditionStrategy {
    fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController, pieceEatingRuler: PieceEatingRuler,
                               pieceMovementStrategy: Map<Int, MovementStrategy>,
                               specialMovementsController: SpecialMovementController
    ): Outcome<Boolean>
}