package boardGame.game

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementManager
import boardGame.movement.MovementManagerController
import boardGame.movement.MovementPerformer
import boardGame.movement.MovementResult
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player
import boardGame.turnsController.TurnsController
import boardGame.winningConditionStrategy.WinningConditionStrategy

class BaseGame(private val board: Board,
               private val turnsController: TurnsController,
               private val pieceEatingRuler: PieceEatingRuler,
               private val movementManager: MovementManager,
               private val movementManagerController: MovementManagerController,
               private val winningCondition: WinningConditionStrategy
): Game {

    override fun makeMovement(player: Player, origin: Vector, destination: Vector): GameMovementResult {
        val actualPlayer: Player = when (val outcome = checkValidConditions(player, origin)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        val movementResult: MovementResult = when (val outcome = tryToPerformMovement(origin, destination, actualPlayer)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }

        val newMovementManager: MovementManager =
            movementManagerController.updateMovementManager(movementManager, movementResult, this)

        //TODO use new movementManager and new movementManagerController
        return getFinalMovementResult(movementResult, newMovementManager, actualPlayer)
    }

    /**
     * Player should be the same that the actual
     * There should be a piece in the origin position
     * Player should be able to control the piece
     * return: the actual player
     */
    private fun checkValidConditions(player: Player, origin: Vector): Outcome<Player> {
        val actualPlayer = when (val outcome = turnsController.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        if (player != actualPlayer) return FailedOutcome("Is not the player turn")

        val piece: Piece = when (val outcome = board.getPieceInPosition(origin)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        if (!player.playerControlColor(piece.getPieceColor()))
            return FailedOutcome("This color is not controlled by the actual player")
        return SuccessfulOutcome(actualPlayer)
    }

    private fun tryToPerformMovement(origin: Vector, destination: Vector, actualPlayer: Player): Outcome<MovementResult> {
        val movementPerformer: MovementPerformer = when (val outcome =
            movementManager.findValidMovementPerformer(pieceEatingRuler, actualPlayer, origin, destination, board)){
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        return movementPerformer.performMovement(origin, destination, board)
    }

    /**
     * Return new GameState or the player that won, in case the winingCondition check failed a Failure outcome
     */
    private fun getFinalMovementResult(
        movementResult: MovementResult,
        newMovementManager: MovementManager,
        actualPlayer: Player
    ): GameMovementResult {
        val newGameState: BaseGame = BaseGame(movementResult.newBoard, turnsController.updateTurnController(movementResult),
            pieceEatingRuler, newMovementManager, movementManagerController, winningCondition)

        val won: Boolean = when (val outcome = winningCondition.checkWinningConditions(newGameState)) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return MovementFailed(outcome.error)
        }
        if (won) return PlayerWon(actualPlayer)

        return MovementSuccessful(newGameState)
    }

    override fun getActualPlayer(): Outcome<Player> = turnsController.getActualPlayer()
    override fun getBoard(): Board = board
    override fun getMovementManager(): MovementManager = movementManager
    override fun getMovementManagerController(): MovementManagerController = movementManagerController
    override fun getPieceEatingRuler(): PieceEatingRuler = pieceEatingRuler
    override fun getTurnController(): TurnsController = turnsController
    override fun getWinningConditions(): WinningConditionStrategy = winningCondition
}