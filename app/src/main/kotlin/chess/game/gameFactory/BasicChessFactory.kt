package chess.game.gameFactory

import boardGame.board.Board
import chess.boardFactories.BaseBoardFiller
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.BaseGame
import boardGame.game.Game
import boardGame.game.GameFactory
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.MovementManagerController
import chess.movementStrategy.gameMovementsFactory.BasicChessMovements
import boardGame.pieceEatingRuler.BasicEatingRuler
import boardGame.player.MulticolorPlayer
import boardGame.player.Player
import boardGame.turnsController.CircleTurnController
import chess.game.ForEveryMovementNextTurn
import chess.winningConditionStrategy.CheckmateWinningCondition
import chess.winningConditionStrategy.TotalAnnihilationWinningCondition

object BasicChessFactory: GameFactory {
    override fun getGame(): Game {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = BaseBoardFiller().fillBoard(board)

        val movements: MovementManager = BasicChessMovements(board).getMovementsManager()
        val movementsController: MovementManagerController = BasicChessMovements(board).getMovementsManagerController()

        return BaseGame(board,
            CircleTurnController(players, 0, ForEveryMovementNextTurn()),
            BasicEatingRuler(),
            movements,
            movementsController,
            CheckmateWinningCondition(),
        )
    }
}