package checkers

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.*
import boardGame.movement.*
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.turnsController.TurnsController
import boardGame.winningConditionStrategy.WinningConditionStrategy
import checkers.game.isPieceAbleToEat

class CheckersGame(private val board: Board,
                   private val actualPlayer: Player,
                   private val actualPiece: Piece?,
                   private val turnsController: TurnsController,
                   private val pieceEatingRuler: PieceEatingRuler,
                   private val movementManager: MovementManager,
                   private val movementManagerController: MovementManagerController,
                   private val winningCondition: WinningConditionStrategy
): Game {
    override fun makeMovement(player: Player, origin: Vector, destination: Vector): GameMovementResult {
        if (player != actualPlayer) return MovementFailed("Is not the player turn")

        val piece: Piece = when (val outcome = board.getPieceInPosition(origin)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        if (actualPiece != null)
            if (actualPiece != piece)
                return MovementFailed("Should continue moving previous piece")

        if (!player.playerControlColor(piece.getPieceColor()))
            return MovementFailed("This color is not controlled by the actual player")

        val movementPerformer: MovementPerformer = when (val outcome =
            movementManager.findValidMovementPerformer(pieceEatingRuler, actualPlayer, origin, destination, board)
        ){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        val movementResult: MovementResult = when (val outcome = movementPerformer.performMovement(origin, destination, board)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }


        val newMovementManager: MovementManager =
            movementManagerController.updateMovementManager(movementManager, movementResult, this)

        val newBoard: Board = movementResult.newBoard

        val nextPlayer: Player
        val newTurnsControllerStatus: TurnsController
        val newActualPiece: Piece?
        if (pieceContinueEating(movementResult, piece, actualPlayer)) {
            newActualPiece = piece
            nextPlayer = actualPlayer
            newTurnsControllerStatus = turnsController
        } else {
            newActualPiece = null

            val getNextPlayer: Pair<Player, TurnsController> = when (val outcome = turnsController.getNextPlayerTurn()){
                is SuccessfulOutcome -> outcome.data
                is FailedOutcome -> return MovementFailed(outcome.error)
            }
            nextPlayer = getNextPlayer.first
            newTurnsControllerStatus = getNextPlayer.second
        }

        val newGameState = CheckersGame(newBoard, nextPlayer, newActualPiece, newTurnsControllerStatus, pieceEatingRuler,
            newMovementManager, movementManagerController, winningCondition)

        val won: Boolean = when (val outcome = winningCondition.checkWinningConditions(newGameState)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }
        if (won) return PlayerWon(actualPlayer)

        return MovementSuccessful(newGameState)
    }

    private fun pieceContinueEating(movementResult: MovementResult, piece: Piece, player: Player): Boolean{
        val pos = when (val outcome = findPiecePos(piece, movementResult.newBoard)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return false
        }
        return hasPieceEaten(movementResult) && isPieceAbleToEat(pos, movementResult.newBoard, player)
    }

    private fun hasPieceEaten(movementResult: MovementResult): Boolean {
        for (movementEvent in movementResult.movementEvents)
            if (movementEvent is Eat) return true
        return false
    }

    private fun findPiecePos(piece: Piece, board: Board): Outcome<Vector> {
        for ((xPiece: Piece, position: Vector) in board.getPiecesAndPosition()) {
            if (xPiece == piece) return SuccessfulOutcome(position)
        }
        return FailedOutcome("Position of piece not found")
    }

    override fun getActualPlayer(): Player = actualPlayer
    override fun getBoard(): Board = board
    override fun getMovementManager(): MovementManager = movementManager
    override fun getMovementManagerController(): MovementManagerController = movementManagerController
    override fun getPieceEatingRuler(): PieceEatingRuler = pieceEatingRuler
    override fun getTurnController(): TurnsController = turnsController
    override fun getWinningConditions(): WinningConditionStrategy = winningCondition
}