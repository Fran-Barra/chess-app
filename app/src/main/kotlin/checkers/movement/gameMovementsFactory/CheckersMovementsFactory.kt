package checkers.movement.gameMovementsFactory

import boardGame.movement.*
import boardGame.movement.movementManager.*
import checkers.movement.performer.JumpAndEatPerformer
import checkers.movement.validator.composedValidators.CheckerMovement
import checkers.movement.validator.composedValidators.EatCheckerMovement
import checkers.movement.validator.composedValidators.EatNormalMovement
import checkers.movement.validator.composedValidators.NormalPieceMovement

object CheckersMovementsFactory: GameMovementsFactory {
    override fun getMovementsManager(): MovementManager {
        val movements: Map<Int, List<Movement>> = mutableMapOf(
            Pair(0, listOf(
                Movement(CheckerMovement.getMovementStrategy(), FromTooMovementPerformer),
                Movement(EatCheckerMovement.getMovementStrategy(), JumpAndEatPerformer)
            )),
            Pair(1, listOf(
                Movement(NormalPieceMovement.getMovementStrategy(), PerformMovementAndPromoteIfPossible(FromTooMovementPerformer, 0)),
                Movement(EatNormalMovement.getMovementStrategy(), PerformMovementAndPromoteIfPossible(JumpAndEatPerformer, 0))
            ))
        )

        return IdMovementManager(movements, GetPieceTypeId())
    }

    override fun getMovementsManagerController(): MovementManagerController {
        return BaseMovementManagerController(listOf())
    }
}