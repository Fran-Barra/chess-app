import board.Board
import board.boardFactory.BaseBoardFiller
import board.boardFactory.RectangularBoardBuilder
import edu.austral.dissis.chess.gui.*
import event.GameEvent
import game.Game
import game.GameEngineAdapter
import game.gameFactory.BasicChessFactory
import javafx.application.Application
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

fun main() {
    Application.launch(ChessGame::class.java)
}

class ChessGame: AbstractChessGameApplication() {
    override val gameEngine: GameEngine = GameEngineAdapter(BasicChessFactory.getGame())
    override val imageResolver: ImageResolver = CachedImageResolver(DefaultImageResolver())
}