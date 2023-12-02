package boardGame

import edu.austral.dissis.chess.gui.*
import boardGame.game.GameEngineAdapter
import boardGame.game.GetPieceFromTypeInStringFormat
import checkers.game.checkersIdToString
import checkers.game.gameFactory.CheckersFactory
import chess.game.chessPieceToString
import chess.game.gameFactory.BasicChessFactory
import javafx.application.Application

fun main() {
    Application.launch(BoardGame::class.java)
}

class BoardGame: AbstractChessGameApplication() {
    override val gameEngine: GameEngine = GameEngineAdapter(
        BasicChessFactory.getGame(),
        GetPieceFromTypeInStringFormat(chessPieceToString.idToString, chessPieceToString.defaultValue)
    )
    override val imageResolver: ImageResolver = CachedImageResolver(DefaultImageResolver())
}