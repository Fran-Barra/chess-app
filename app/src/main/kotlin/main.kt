import board.Board
import board.MapBoard
import board.boardFactory.BaseBoardFiller
import board.boardFactory.RectangularBoardBuilder
import edu.austral.dissis.chess.gui.*
import event.GameEvent
import javafx.application.Application
import movement.MovementStrategy
import movement.SpecialMovement
import movement.movementStrategy.*
import movement.specialMovement.BaseSpecialMovementController
import movement.unionMovement.AndUnionMovement
import movement.unionMovement.OrUnionMovement
import piece.BasicPiece
import piece.Piece
import pieceEatingRuler.BasicEatingRuler
import player.MulticolorPlayer
import player.Player
import turnsController.CircleTurnController
import vector.Vector
import winningConditionStrategy.CheckmateWinningCondition

fun main() {
    Application.launch(ChessGame::class.java)
}

class ChessGame: AbstractChessGameApplication() {
    override val gameEngine: GameEngine = GameEngineAdapter(buildGame())
    override val imageResolver: ImageResolver = CachedImageResolver(DefaultImageResolver())

    private fun buildGame(): Game{
        val players: List<Player> = listOf<Player>(MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1)))

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()

        board = BaseBoardFiller().fillBoard(board)


        //TODO: add strategies
        val movementStrategies: MutableMap<Int, MovementStrategy> = mutableMapOf()
        movementStrategies[5] = buildPawnRules()
        //TODO: fill this
        val specialMovements: Map<Piece, List<Pair<List<GameEvent>, SpecialMovement>>> = mapOf()

        return Game(
            board,
            players[0],
            CircleTurnController(players, 1),
            BasicEatingRuler(),
            movementStrategies,
            BaseSpecialMovementController(specialMovements),
            CheckmateWinningCondition(),
        )
    }

    private fun buildPawnRules(): MovementStrategy{
        val con1 = AndUnionMovement(listOf(CheckVerticalPositive(), VerticalMovement(), DontEatInDestini()))
        val con2 = AndUnionMovement(listOf(DiagonalMovement(), EatInDestini()))
        return AndUnionMovement(listOf(OrUnionMovement(listOf(con1, con2)), DistanceSmallerThanX(2)))
    }
}