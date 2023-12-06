package chekers

import boardGame.board.Board
import boardGame.board.Vector
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.BaseGame
import boardGame.game.Game
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.MovementManagerController
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
import org.junit.jupiter.api.Test
import testMovement

class MovementTest {
    @Test
    fun testPawn() {
        testMovement(
            getGame { board -> fillWithOnePieceInPos(board, 1, Vector(4, 4)) },
            Vector(4,4),
            listOf(Vector(3, 3), Vector(5, 3))
        )
    }

    @Test
    fun notMoveIfOtherCanEat() {
        testMovement(
            getGame { board -> fillBoardWithOneAbleToEatTwo(board) },
            Vector(2,2),
            listOf()
        )
    }

    @Test
    fun testQueen() {
        testMovement(
            getGame { board -> fillWithOnePieceInPos(board, 0, Vector(4, 4)) },
            Vector(4,4),
            listOf(Vector(3,5), Vector(5,5), Vector(3,3), Vector(5, 3))
        )
    }

    private fun fillWithOnePieceInPos(board: Board, type: Int, pos: Vector): Board =
        board.addPiece(BasicPiece(type, 0), pos)

    private fun fillBoardWithOneAbleToEatTwo(board: Board): Board {
        var newBoard = board.addPiece(BasicPiece(1, 0), Vector(2, 7))
        newBoard = newBoard.addPiece(BasicPiece(1, 1), Vector(5, 2))
        newBoard = newBoard.addPiece(BasicPiece(1, 1), Vector(5, 4))
        return newBoard.addPiece(BasicPiece(1, 0), Vector(4, 5))
    }


    private fun getGame(fill: (Board) -> Board): Game {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = fill(board)

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
}