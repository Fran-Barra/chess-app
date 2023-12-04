package boardGame.movement.movementManager

import FailedOutcome
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.game.Game
import boardGame.movement.MovementPerformer
import boardGame.player.Player

class IdMovementManager(
    private val idsMovements: Map<Int, List<Movement>>,
    private val idGetter: GetIdStrategy
) : MovementManager {

    override fun findValidMovementPerformer(player: Player, actual: Vector, destination: Vector, game: Game): Outcome<MovementPerformer> {
        val id: Int = when (val outcome = idGetter.getId(player, actual, destination, game.getBoard())) {
            is SuccessfulOutcome -> outcome.data
            is FailedOutcome -> return FailedOutcome(outcome.error)
        }

        val movementsOfId: List<Movement> = idsMovements[id]?:
            return FailedOutcome("This piece type don't have movements")

        if (movementsOfId.isEmpty())
            return FailedOutcome("This piece type don't have movements")

        for (movement in movementsOfId) {
            if (!movement.validator.validate(player, actual, destination, game)) continue
            return SuccessfulOutcome(movement.performer)
        }
        return FailedOutcome("No valid movement found")
    }

    override fun addMovement(id: Int, movement: Movement): MovementManager {
        val newMap: MutableMap<Int, List<Movement>> = idsMovements.toMutableMap()
        val movementList = newMap[id] ?: emptyList()
        val updatedList = movementList + movement
        newMap[id] = updatedList
        return IdMovementManager(newMap, idGetter)
    }

    override fun removeMovement(id: Int, movement: Movement): MovementManager {
        val newMap: MutableMap<Int, List<Movement>> = idsMovements.toMutableMap()
        val movementList = newMap[id] ?: emptyList()
        val updatedList = movementList - movement
        newMap[id] = updatedList
        return IdMovementManager(newMap, idGetter)
    }
}

interface GetIdStrategy{
    fun getId(player: Player, actual: Vector, destination: Vector, board: Board): Outcome<Int>
}