package checkers.game.gameFactory

import boardGame.board.Board
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.Game
import boardGame.game.GameFactory
import boardGame.movement.MovementManager
import boardGame.movement.MovementManagerController
import boardGame.pieceEatingRuler.BasicEatingRuler
import boardGame.player.MulticolorPlayer
import boardGame.player.Player
import boardGame.turnsController.CircleTurnController
import checkers.boardFactory.CheckersBoardFiller
import checkers.game.CheckersGame
import checkers.movement.gameMovementsFactory.CheckersMovementsFactory
import checkers.winningCondition.TotalAnnihilationWinningCondition

object CheckersFactory: GameFactory {
    override fun getGame(): Game {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = CheckersBoardFiller.fillBoard(board)

        val movements: MovementManager = CheckersMovementsFactory.getMovementsManager()
        val movementsController: MovementManagerController = CheckersMovementsFactory.getMovementsManagerController()

        return CheckersGame(board, players[0],
            null,
            CircleTurnController(players, 1),
            BasicEatingRuler(),
            movements,
            movementsController,
            TotalAnnihilationWinningCondition()
        )
    }
}