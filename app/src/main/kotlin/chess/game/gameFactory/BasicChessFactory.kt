package chess.game.gameFactory

import boardGame.board.Board
import chess.boardFactories.BaseBoardFiller
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.GameFactory
import boardGame.movement.GameEvent
import chess.game.ChessGame
import boardGame.movement.MovementValidator
import chess.movementStrategy.gameMovementsFactory.BasicChessMovements
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.BasicEatingRuler
import boardGame.player.MulticolorPlayer
import boardGame.player.Player
import boardGame.turnsController.CircleTurnController
import chess.winningConditionStrategy.TotalAnnihilationWinningCondition

object BasicChessFactory: GameFactory {
    override fun getGame(): ChessGame {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = BaseBoardFiller().fillBoard(board)

        val movementStrategies: Map<Int, MovementValidator> = BasicChessMovements.getMovementsStrategies()

        //TODO: fill this
        val specialMovements: Map<Piece, List<Pair<List<GameEvent>, SpecialMovement>>> = mapOf()

        //TODO: change winning condition
        return ChessGame(board, players[0],
            CircleTurnController(players, 1),
            BasicEatingRuler(),
            movementStrategies,
            BaseSpecialMovementController(specialMovements),
            TotalAnnihilationWinningCondition(),
        )
    }
}