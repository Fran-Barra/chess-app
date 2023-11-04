package boardGame.movement

import Outcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.unionMovement.MovementPerformer
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player

interface MovementManager {
    fun findValidMovementPerformer(
        pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector, board: Board
    ): Outcome<MovementPerformer>

    fun addMovement(movement: Movement): MovementManager
    //TODO: think of how to use this or is convenient in another way
    fun removeMovement(movement: Movement): MovementManager
}
data class Movement(val validator: MovementValidator, val performer: MovementPerformer)