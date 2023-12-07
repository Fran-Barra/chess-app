package chess

import FailedOutcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.*
import boardGame.movement.movementManager.MovementManager
import boardGame.movement.movementManager.MovementManagerController
import boardGame.piece.BasicPiece
import boardGame.pieceEatingRuler.BasicEatingRuler
import boardGame.player.MulticolorPlayer
import boardGame.player.Player
import boardGame.turnsController.CircleTurnController
import chess.game.ForEveryMovementNextTurn
import chess.movementStrategy.gameMovementsFactory.BasicChessMovements
import chess.winningConditionStrategy.TotalAnnihilationWinningCondition
import org.junit.jupiter.api.Test
import testMovement


class MovementTest {
    @Test
    fun kingTest(){
        testMovement(
            buildChessGameWithPieceInPos(0, Vector(4, 4)),
            Vector(4, 4),
            listOf(
                Vector(3, 3), Vector(3, 4), Vector(3, 5), Vector(4, 3),
                Vector(4, 5), Vector(5, 3), Vector(5, 4), Vector(5, 5)
            )
        )
    }

    @Test
    fun queenTest(){
        testMovement(
            buildChessGameWithPieceInPos(1, Vector(4, 4)),
            Vector(4, 4),
            (horizontalAndVertical(Vector(4, 4), 8, 8) + diagonals(8)).filter { it != Vector(4, 4) }
        )
    }

    @Test
    fun bishopTest() {
        testMovement(
            buildChessGameWithPieceInPos(2, Vector(4, 4)),
            Vector(4, 4),
            diagonals(8).filter { it != Vector(4, 4) }
        )
    }

    @Test
    fun knightTest() {
        testMovement(
            buildChessGameWithPieceInPos(3, Vector(4, 4)),
            Vector(4, 4),
            listOf(
                Vector(2, 5), Vector(2, 3), Vector(3, 2), Vector(5, 2),
                Vector(3, 6), Vector(5, 6), Vector(6, 5), Vector(6, 3)
            )
        )
    }

    @Test
    fun rookTest() {
        testMovement(
            buildChessGameWithPieceInPos(4, Vector(4, 4)),
            Vector(4, 4),
            horizontalAndVertical(Vector(4, 4), 8, 8).filter { it != Vector(4, 4) }
        )
    }

    /**
     * The rooks didn't move and the pieces were not in check
     */
    @Test
    fun castlingTest() {
        testMovement(
            buildChessGameWithFiller { board ->  baseCastlingFiller(board) },
            Vector(5, 8),
            listOf(
                Vector(4, 8), Vector(4,7), Vector(5, 7), Vector(6,7), Vector(6, 8),
                Vector(1,8), Vector(8,8)
            )
        )
    }

    @Test
    fun movementOfRookDisableCastlingWithThatRook() {
        val game = buildChessGameWithFiller { board -> baseCastlingFiller(board) }
        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> {assert(false) {"Need a player"}; return}
        }

        val leftRookMoved = when (val outcome = game.makeMovement(actualPlayer, Vector(1, 8), Vector(2, 8))) {
            is MovementFailed -> {assert(false) {"failed: $outcome"}; return}
            is MovementSuccessful -> outcome.newGameState
            is PlayerWon -> {assert(false) {"won: $outcome"}; return}
        }

        val enemyPlayer = when (val outcome = leftRookMoved.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> {assert(false) {"Need a player"}; return}
        }

        val testState = when (val outcome = leftRookMoved.makeMovement(enemyPlayer, Vector(1, 1), Vector(1,2))) {
            is MovementFailed -> {assert(false) {"failed: $outcome"}; return}
            is MovementSuccessful -> outcome.newGameState
            is PlayerWon -> {assert(false) {"won: $outcome"}; return}
        }

        testMovement(
            testState,
            Vector(5, 8),
            listOf(
                Vector(4, 8), Vector(4,7), Vector(5, 7), Vector(6,7), Vector(6, 8),
                Vector(8,8)
            )
        )
    }

    @Test
    fun movementOfKingDisableCastling() {
        val game = buildChessGameWithFiller { board -> baseCastlingFiller(board) }
        val actualPlayer = when (val outcome = game.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> {assert(false) {"Need a player"}; return}
        }

        val kingMoved = when (val outcome = game.makeMovement(actualPlayer, Vector(5, 8), Vector(4, 8))) {
            is MovementFailed -> {assert(false) {"failed: $outcome"}; return}
            is MovementSuccessful -> outcome.newGameState
            is PlayerWon -> {assert(false) {"won: $outcome"}; return}
        }

        val enemyPlayer = when (val outcome = kingMoved.getActualPlayer()) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> {assert(false) {"Need a player"}; return}
        }

        val testState = when (val outcome = kingMoved.makeMovement(enemyPlayer, Vector(1, 1), Vector(1,2))) {
            is MovementFailed -> {assert(false) {"failed: $outcome"}; return}
            is MovementSuccessful -> outcome.newGameState
            is PlayerWon -> {assert(false) {"won: $outcome"}; return}
        }

        testMovement(
            testState,
            Vector(4, 8),
            listOf(
                Vector(3, 8), Vector(3,7), Vector(4, 7), Vector(5,7), Vector(5, 8)
            )
        )
    }

    @Test
    fun pawnTest() {
        testMovement(
            buildChessGameWithPieceInPos(5, Vector(4, 4)),
            Vector(4, 4),
            listOf(Vector(4, 3), Vector(4, 2))
        )
    }

    //TODO: test OnPassant
    //TODO: test Promotion

    private fun buildChessGameWithFiller(fill: (Board) -> Board): Game{
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = fill(board)

        val movements: MovementManager = BasicChessMovements(board).getMovementsManager()
        val movementsController: MovementManagerController = BasicChessMovements(board).getMovementsManagerController()

        //TODO: checkmate winning condition
        return BaseGame(board,
            CircleTurnController(players, 0, ForEveryMovementNextTurn()),
            BasicEatingRuler(),
            movements,
            movementsController,
            TotalAnnihilationWinningCondition(),
        )
    }

    private fun baseCastlingFiller(board: Board): Board {
        var newBoard = board.addPiece(BasicPiece(0, 0), Vector(5, 8))

        newBoard = newBoard.addPiece(BasicPiece(4, 0), Vector(1, 8))
        newBoard = newBoard.addPiece(BasicPiece(4, 0), Vector(8,8))

        return newBoard.addPiece(BasicPiece(5, 1), Vector(1,1))
    }

    private fun buildChessGameWithPieceInPos(pieceType: Int, pos: Vector): Game{
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = board.addPiece(BasicPiece(pieceType, 0), pos)

        val movements: MovementManager = BasicChessMovements(board).getMovementsManager()
        val movementsController: MovementManagerController = BasicChessMovements(board).getMovementsManagerController()

        //TODO: checkmate winning condition
        return BaseGame(board,
            CircleTurnController(players, 0, ForEveryMovementNextTurn()),
            BasicEatingRuler(),
            movements,
            movementsController,
            TotalAnnihilationWinningCondition(),
        )
    }

    private fun horizontalAndVertical(center: Vector, maxX: Int, maxY: Int) =
        fillVertical(center.x, maxY) + fillHorizontal(center.y, maxX)
    private fun fillHorizontal(y: Int, maxX: Int): List<Vector> = (1..maxX).map { Vector(it, y) }
    private fun fillVertical(x: Int, maxY: Int): List<Vector> = (1..maxY).map { Vector(x, it) }

    private fun diagonals(max: Int): List<Vector> =
        diagonalDownUp() + diagonalUpDown(max)
    private fun diagonalUpDown(max: Int): List<Vector> = (1..max).map { Vector(it, it) }
    private fun diagonalDownUp(): List<Vector> =
        listOf(
            Vector(1, 7), Vector(2, 6), Vector(3, 5), Vector(4, 4),
            Vector(5, 3), Vector(6, 2), Vector(7, 1)
        )
}