package checkers.game

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.*
import boardGame.movement.MovementStrategy
import boardGame.movement.SpecialMovementController
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.turnsController.TurnsController
import boardGame.winningConditionStrategy.WinningConditionStrategy
import chess.game.ChessGame

class CheckersGame(private val board: Board,
                   private val actualPlayer: Player,
                   private val turnsController: TurnsController,
                   private val pieceEatingRuler: PieceEatingRuler,
                   private val pieceMovementStrategy: Map<Int, MovementStrategy>,
                   private val specialMovementsController: SpecialMovementController,
                   private val winningCondition: WinningConditionStrategy
): Game {
    override fun getActualPlayer(): Player = actualPlayer
    override fun getBoard(): Board = board

    override fun makeMovement(player: Player, origin: Vector, destination: Vector): GameMovementResult {
        if (player != actualPlayer) return MovementFailed("Is not the player turn")

        val piece: Piece = when (val outcome = board.getPieceInPosition(origin)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        if (!player.playerControlColor(piece.getPieceColor()))
            return MovementFailed("This color is not controlled by the actual player")

        //TODO("Integrate special movements")
        //specialMovementsController.checkMovement(boardGame.pieceEatingRuler, player, origin, destination, board)

        val movementStrategy: MovementStrategy = pieceMovementStrategy[piece.getPieceType()]
            ?: return MovementFailed("Piece type don't match with the rules of the chess.game")

        if (!movementStrategy.checkMovement(pieceEatingRuler, player, origin, destination, board))
            return MovementFailed("Movement is not valid for this boardGame.piece")

        val newBoard: Board = board.movePiece(piece, destination)

        //TODO: special movement strategy new is needed
        val won: Boolean = when (val outcome = winningCondition.checkWinningConditions(board, actualPlayer, turnsController,
            pieceEatingRuler, pieceMovementStrategy, specialMovementsController)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        if (won) return PlayerWon(actualPlayer)

        val getNextPlayer: Pair<Player, TurnsController> = when (val outcome = turnsController.getNextPlayerTurn()){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        //TODO: if MoveWith eat and it is posible to

        val nextPlayer: Player = getNextPlayer.first
        val newTurnsControllerStatus: TurnsController = getNextPlayer.second
        //TODO: special movement strategy new is needed
        return MovementSuccessful(
            ChessGame(newBoard, nextPlayer, newTurnsControllerStatus, pieceEatingRuler,
                pieceMovementStrategy, specialMovementsController, winningCondition)
        )
    }
}