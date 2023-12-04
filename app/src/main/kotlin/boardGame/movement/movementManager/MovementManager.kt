package boardGame.movement.movementManager

import Outcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementPerformer
import boardGame.movement.MovementValidator
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

interface MovementManager {
    fun findValidMovementPerformer(player: Player, actual: Vector, destination: Vector, game: Game):
            Outcome<MovementPerformer>

    /**
     * movement is the movement to add, while id is from where, this depends on the implementation
     */
    fun addMovement(id: Int, movement: Movement): MovementManager
    //TODO: think of how to use this or is convenient in another way
    /**
     * movement is the movement to remove, while id is from where, this depends on the implementation
     */
    fun removeMovement(id: Int, movement: Movement): MovementManager
}

//TODO: if you want to execute movements with priority add a int priority in movement
data class Movement(val validator: MovementValidator, val performer: MovementPerformer)