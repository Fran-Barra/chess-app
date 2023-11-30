package boardGame.turnsController

import Outcome
import boardGame.movement.MovementResult
import boardGame.player.Player

//TODO: have a method that given a movement validates if the movement is valid given the turn (to extract logic from checkers)
interface TurnsController {
    /**Get a list of all the players one time (not guaranteed order)*/
    fun getPlayers(): List<Player>

    /**Get the actual player*/
    fun getActualPlayer(): Outcome<Player>

    /**add a player to the controller, where depends on the implementation*/
    fun addPlayer(player: Player): TurnsController

    /**When movement happens, this method will update the controller with the corresponding player turn*/
    fun updateTurnController(movementResult: MovementResult): TurnsController
}

