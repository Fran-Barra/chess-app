package boardGame

import chess.game.FromPieceTypeToString
import edu.austral.dissis.chess.gui.*
import boardGame.game.GameEngineAdapter
import chess.game.gameFactory.RebellionChessFactory
import javafx.application.Application

fun main() {
    Application.launch(BoardGame::class.java)
}

class BoardGame: AbstractChessGameApplication() {
    override val gameEngine: GameEngine = GameEngineAdapter(RebellionChessFactory.getGame(), FromPieceTypeToString())
    override val imageResolver: ImageResolver = CachedImageResolver(DefaultImageResolver())
}