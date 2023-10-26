package game.gameFactory

import board.Board
import board.boardFactory.BaseBoardFiller
import board.boardFactory.RectangularBoardBuilder
import event.GameEvent
import game.Game
import movement.MovementStrategy
import movement.SpecialMovement
import movement.movementStrategy.gameMovementsFactory.BasicChessMovements
import movement.specialMovement.BaseSpecialMovementController
import piece.Piece
import pieceEatingRuler.BasicEatingRuler
import player.MulticolorPlayer
import player.Player
import turnsController.CircleTurnController
import winningConditionStrategy.CheckmateWinningCondition
import winningConditionStrategy.TotalAnnihilationWinningCondition

object BasicChessFactory: GameFactory {
    override fun getGame(): Game {
        val players: List<Player> = listOf<Player>(
            MulticolorPlayer(0, listOf(0)),
            MulticolorPlayer(1, listOf(1))
        )

        var board: Board = RectangularBoardBuilder(8, 8).createNewEmptyBoard()
        board = BaseBoardFiller().fillBoard(board)

        val movementStrategies: Map<Int, MovementStrategy> = BasicChessMovements.getMovementsStrategies()

        //TODO: fill this
        val specialMovements: Map<Piece, List<Pair<List<GameEvent>, SpecialMovement>>> = mapOf()

        //TODO: change winning condition
        return Game(board, players[0],
            CircleTurnController(players, 1),
            BasicEatingRuler(),
            movementStrategies,
            BaseSpecialMovementController(specialMovements),
            TotalAnnihilationWinningCondition(),
        )
    }
}