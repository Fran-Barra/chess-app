package boardGame.player

interface Player {
    fun playerControlColor(colorId: Int) : Boolean
    fun getPlayerId(): Int
}