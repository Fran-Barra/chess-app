package boardGame.winningConditionStrategy

import Outcome
import boardGame.board.Board
import boardGame.movement.MovementValidator
import boardGame.movement.SpecialMovementController
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.turnsController.TurnsController

interface WinningConditionStrategy {
    fun checkWinningConditions(board: Board, actualPlayer: Player, turnsController: TurnsController, pieceEatingRuler: PieceEatingRuler,
                               pieceMovementValidator: Map<Int, MovementValidator>,
                               specialMovementsController: SpecialMovementController
    ): Outcome<Boolean>
}