package chess.game

import FailedOutcome
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

class ChessGame (private val board: Board,
                 private val actualPlayer: Player,
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

        val getNextPlayer: Pair<Player, TurnsController> = when (val outcome = turnsController.getNextPlayerTurn()){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }
        val nextPlayer: Player = getNextPlayer.first
        val newTurnsControllerStatus: TurnsController = getNextPlayer.second

        //TODO use new movementManager and new movementManagerController
        val newGameState: ChessGame = ChessGame(newBoard, nextPlayer, newTurnsControllerStatus, pieceEatingRuler,
                newMovementManager, movementManagerController, winningCondition)

        val won: Boolean = when (val outcome = winningCondition.checkWinningConditions(newGameState)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }
        if (won) return PlayerWon(actualPlayer)

        return MovementSuccessful(newGameState)
    }

    override fun getActualPlayer(): Player = actualPlayer
    override fun getBoard(): Board = board
    override fun getMovementManager(): MovementManager = movementManager
    override fun getMovementManagerController(): MovementManagerController = movementManagerController
    override fun getPieceEatingRuler(): PieceEatingRuler = pieceEatingRuler
    override fun getTurnController(): TurnsController = turnsController
    override fun getWinningConditions(): WinningConditionStrategy = winningCondition
}

//TODO: implement this method and don't have code with same objective
//public fun chessGameMakeNewState(game: ChessGame, )