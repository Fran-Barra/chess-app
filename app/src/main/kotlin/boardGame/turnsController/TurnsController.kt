package boardGame.turnsController

import Outcome
import boardGame.player.Player

//TODO: have a method that gets the actual player and another that change to the new state
interface TurnsController {
    //get the next player turn and a turn controller with a modified state.
    fun getNextPlayerTurn() : Outcome<Pair<Player, TurnsController>>
    //add player to the controller, where is put depends on the implementation.
    fun addPlayer(player: Player): TurnsController
}