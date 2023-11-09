package boardGame.game

import chess.game.ChessGame

interface GameFactory {
    fun getGame(): Game
}