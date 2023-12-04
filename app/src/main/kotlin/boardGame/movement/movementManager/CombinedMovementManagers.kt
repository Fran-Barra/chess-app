package boardGame.movement.movementManager

import FailedOutcome
import IdGenerator
import Outcome
import SuccessfulOutcome
import boardGame.board.Board
import boardGame.board.Vector
import boardGame.movement.MovementPerformer
import boardGame.pieceEatingRuler.PieceEatingRuler
import boardGame.player.Player


class CombinedMovementManagers(
    private val managers: Map<MovementManagerType, MovementManager>,
    private val classifier: IdTypeClassifier
): MovementManager {

    /**
     * Prioritize the first element of the map
     */
    override fun findValidMovementPerformer(
        pieceEatingRuler: PieceEatingRuler, player: Player, actual: Vector, destination: Vector, board: Board
    ): Outcome<MovementPerformer> {
        for (manager in managers.values) {
            when (val outcome = manager.findValidMovementPerformer(
                pieceEatingRuler, player, actual, destination, board
            )){
                is SuccessfulOutcome -> return outcome
                is FailedOutcome -> {}
            }
        }
        return FailedOutcome("No valid movement found")
    }

    /**
     * If try to add a movement to a not existing MovementManagerType, it will be ignored
     */
    override fun addMovement(id: Int, movement: Movement): MovementManager {
        val type = classifier.classify(id)
        val toUpdate: MovementManager = managers[type]?: return this
        val updated: MovementManager = toUpdate.addMovement(id, movement)
        val newMap = managers.toMutableMap()
        newMap[type] = updated
        return CombinedMovementManagers(newMap, classifier)
    }

    override fun removeMovement(id: Int, movement: Movement): MovementManager {
        val type = classifier.classify(id)
        val toUpdate: MovementManager = managers[type]?: return this
        val updated: MovementManager = toUpdate.removeMovement(id, movement)
        val newMap = managers.toMutableMap()
        newMap[type] = updated
        return CombinedMovementManagers(newMap, classifier)
    }
}

enum class MovementManagerType{
    Id_Piece, Id_Piece_Type
}

interface IdTypeClassifier {
    fun classify(id: Int): MovementManagerType
}

class StandardMovementManagerClassifier: IdTypeClassifier {
    override fun classify(id: Int): MovementManagerType =
        if (id < IdGenerator.getStartingPoint()) {MovementManagerType.Id_Piece_Type}
        else {MovementManagerType.Id_Piece}
}