package checkers.game

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

        val pieceR: Result<Piece> = board.getPieceInPosition(origin)
        if (pieceR.isFailure) return manageFailure(pieceR, "board", "get boardGame.piece in position")
        val piece: Piece = pieceR.getOrNull()!!

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
        val wonR: Result<Boolean> = winningCondition.checkWinningConditions(board, actualPlayer, turnsController,
            pieceEatingRuler, pieceMovementStrategy, specialMovementsController)
        if (wonR.isFailure)
            return manageFailure(wonR, "WinningConditionStrategy", "checkWinningConditions")

        if (wonR.getOrNull()!!) return PlayerWon(actualPlayer)

        val getNextPlayer: Result<Pair<Player, TurnsController>> = turnsController.getNextPlayerTurn()

        //TODO: if MoveWith eat and it is posible to
        if (getNextPlayer.isFailure)
            return manageFailure(getNextPlayer, "TurnController", "getNextPlayerTurn")

        val nextPlayer: Player = getNextPlayer.getOrNull()?.first!!
        val newTurnsControllerStatus: TurnsController = getNextPlayer.getOrNull()?.second!!
        //TODO: special movement strategy new is needed
        return MovementSuccessful(
            ChessGame(newBoard, nextPlayer, newTurnsControllerStatus, pieceEatingRuler,
                pieceMovementStrategy, specialMovementsController, winningCondition)
        )
    }

    private fun <T>manageFailure(result: Result<T>,obj: String, action: String): MovementFailed {
        val exception: Throwable? = result.exceptionOrNull()
        return MovementFailed(
            if (exception != null) { exception.message!!}
            else {"A not specified error happened within the $obj performing the action: $action"}
        )
    }
}