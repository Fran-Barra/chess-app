package boardGame.turnsController

import Outcome
import boardGame.player.Player

//TODO: have a method that gets the actual player and another that change to the new state
//TODO: have a method that given a movement validates if the movement is valid given the turn (to extract logic from checkers)
interface TurnsController {
    //get the next player turn and a turn controller with a modified state.
    fun getNextPlayerTurn() : Outcome<Pair<Player, TurnsController>>
    //add player to the controller, where is put depends on the implementation.
    fun addPlayer(player: Player): TurnsController
}