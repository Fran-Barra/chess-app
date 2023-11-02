package boardGame.player

import boardGame.player.Player

class MulticolorPlayer(private val playerId: Int, private val colors: Collection<Int>): Player {
    override fun playerControlColor(colorId: Int): Boolean = colors.contains(colorId)
    override fun getPlayerId(): Int {
        return playerId
    }
}