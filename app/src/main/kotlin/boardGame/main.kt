package boardGame

import chess.game.FromPieceTypeToString
import edu.austral.dissis.chess.gui.*
import chess.game.GameEngineAdapter
import chess.game.gameFactory.RebellionChessFactory
import javafx.application.Application

fun main() {
    //TODO: change all Result<T> to my own result
    Application.launch(BoardGame::class.java)
}

class BoardGame: AbstractChessGameApplication() {
    override val gameEngine: GameEngine = GameEngineAdapter(RebellionChessFactory.getGame(), FromPieceTypeToString())
    override val imageResolver: ImageResolver = CachedImageResolver(DefaultImageResolver())
}