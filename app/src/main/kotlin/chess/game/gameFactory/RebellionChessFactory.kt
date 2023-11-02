package chess.game.gameFactory

import boardGame.board.Board
import chess.boardFactories.RebellionBoardFiller
import boardGame.board.boardFactory.RectangularBoardBuilder
import boardGame.game.GameFactory
import boardGame.movement.GameEvent
import chess.game.ChessGame
import boardGame.movement.MovementStrategy
import boardGame.movement.SpecialMovement
import chess.movementStrategy.gameMovementsFactory.RebellionChessMovements
import boardGame.movement.specialMovement.BaseSpecialMovementController
import boardGame.piece.Piece
import boardGame.pieceEatingRuler.BasicEatingRuler
import boardGame.player.MulticolorPlayer
import boardGame.player.Player
import boardGame.turnsController.CircleTurnController
import chess.winningConditionStrategy.TotalAnnihilationWinningCondition

object RebellionChessFactory: GameFactory {
    override fun getGame(): ChessGame {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )
        var board: Board = RectangularBoardBuilder(9, 10).createNewEmptyBoard()
        board = RebellionBoardFiller().fillBoard(board)

        val movementStrategies: Map<Int, MovementStrategy> = RebellionChessMovements.getMovementsStrategies()

        //TODO: fill this
        val specialMovements: Map<Piece, List<Pair<List<GameEvent>, SpecialMovement>>> = mapOf()

        return ChessGame(
            board,
            players[0],
            CircleTurnController(players, 1),
            BasicEatingRuler(),
            movementStrategies,
            BaseSpecialMovementController(specialMovements),
            TotalAnnihilationWinningCondition()
        )
    }
}