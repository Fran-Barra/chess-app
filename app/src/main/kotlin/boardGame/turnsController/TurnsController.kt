package boardGame.turnsController

import Outcome
import boardGame.player.Player

interface TurnsController {
    //get the next player turn and a turn controller with a modified state.
    fun getNextPlayerTurn() : Outcome<Pair<Player, TurnsController>>
    //add player to the controller, where is put depends on the implementation.
    fun addPlayer(player: Player): TurnsController
}