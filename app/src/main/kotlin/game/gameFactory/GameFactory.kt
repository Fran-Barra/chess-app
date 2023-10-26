package game.gameFactory

import game.Game

interface GameFactory {
    fun getGame(): Game
}