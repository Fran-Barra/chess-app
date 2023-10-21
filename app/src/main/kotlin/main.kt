import board.Board
import board.MapBoard
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
        board = fillBoard(board);


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

    private fun fillBoard(board: Board): Board{
        var filledBoard = board
        filledBoard = fillRowWithPawns(filledBoard, 0, 7)
        filledBoard = fillRowWithPawns(filledBoard, 1, 2)

        filledBoard = fillRowWithBasicPieces(filledBoard, 0, 8)
        filledBoard = fillRowWithBasicPieces(filledBoard, 1, 1)

        return filledBoard
    }

    private fun fillRowWithPawns(filledBoard: Board, color: Int, row: Int): Board {
        var board = filledBoard
        for (x in 1..8)
            board = board.addPiece(BasicPiece(5, color), Vector(x, row))
        return board
    }

    private fun fillRowWithBasicPieces(board: Board, color: Int, row: Int): Board {
        var fillBoard: Board = board
        //rooks
        fillBoard = fillBoard.addPiece(BasicPiece(4, color), Vector(8, row))
        fillBoard = fillBoard.addPiece(BasicPiece(4, color), Vector(1, row))

        //knights
        fillBoard = fillBoard.addPiece(BasicPiece(3, color), Vector(7, row))
        fillBoard = fillBoard.addPiece(BasicPiece(3, color), Vector(2, row))

        //bishops
        fillBoard = fillBoard.addPiece(BasicPiece(2, color), Vector(6, row))
        fillBoard = fillBoard.addPiece(BasicPiece(2, color), Vector(3, row))

        //king and queen
        fillBoard = fillBoard.addPiece(BasicPiece(0, color), Vector(5, row))
        fillBoard = fillBoard.addPiece(BasicPiece(1, color), Vector(4, row))

        return fillBoard
    }

    private fun buildPawnRules(): MovementStrategy{
        val con1 = AndUnionMovement(listOf(CheckVerticalPositive(), VerticalMovement(), DontEatInDestini()))
        val con2 = AndUnionMovement(listOf(DiagonalMovement(), EatInDestini()))
        return AndUnionMovement(listOf(OrUnionMovement(listOf(con1, con2)), DistanceSmallerThanX(2)))
    }
}