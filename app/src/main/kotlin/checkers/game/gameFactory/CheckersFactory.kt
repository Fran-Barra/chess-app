package checkers.game.gameFactory

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.BaseGame
import boardGame.game.Game
import boardGame.game.GameFactory
import boardGame.movement.MovementManager
import boardGame.movement.MovementManagerController
import boardGame.piece.BasicPiece
import boardGame.pieceEatingRuler.BasicEatingRuler
import boardGame.player.MulticolorPlayer
import boardGame.player.Player
import boardGame.turnsController.CircleTurnController
import boardGame.winningConditionStrategy.OrUnionWiningCondition
import checkers.game.IfNoKillNextTurn
import checkers.movement.gameMovementsFactory.CheckersMovementsFactory
import checkers.winningCondition.OtherPlayerNoMovementsWC
import checkers.winningCondition.TotalAnnihilationWinningCondition

object CheckersFactory: GameFactory {
    override fun getGame(): Game {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = createTestBoard(board)

        val movements: MovementManager = CheckersMovementsFactory.getMovementsManager()
        val movementsController: MovementManagerController = CheckersMovementsFactory.getMovementsManagerController()

        return BaseGame(
            board,
            CircleTurnController(players, 0, IfNoKillNextTurn()),
            BasicEatingRuler(),
            movements,
            movementsController,
            OrUnionWiningCondition(
                listOf(TotalAnnihilationWinningCondition(), OtherPlayerNoMovementsWC())
            )
        )

    }

    private fun createTestBoard(board: Board): Board {
        var newBoard = board.addPiece(BasicPiece(1, 0), Vector(6, 2))
        return  newBoard.addPiece(BasicPiece(1, 1), Vector(4, 2))
    }
}