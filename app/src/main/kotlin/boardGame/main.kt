package boardGame

import edu.austral.dissis.chess.gui.*
import boardGame.game.GameEngineAdapter
import boardGame.game.GetPieceFromTypeInStringFormat
import checkers.game.checkersIdToString
import checkers.game.gameFactory.CheckersFactory
import javafx.application.Application

fun main() {
    Application.launch(BoardGame::class.java)
}

class BoardGame: AbstractChessGameApplication() {
    override val gameEngine: GameEngine = GameEngineAdapter(
        CheckersFactory.getGame(),
        GetPieceFromTypeInStringFormat(checkersIdToString.idToString, checkersIdToString.defaultValue)
    )
    override val imageResolver: ImageResolver = CachedImageResolver(DefaultImageResolver())
}